package com.veremoonstudio.retirementtimer

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingManager(private val context: Context) : PurchasesUpdatedListener {

    private val appContext = context.applicationContext

    private val _isPremiumUnlocked = MutableStateFlow(false)
    val isPremiumUnlocked: StateFlow<Boolean> = _isPremiumUnlocked.asStateFlow()

    private val _productPrices = MutableStateFlow<Map<String, String>>(
        mapOf(
            PRODUCT_LIFETIME to "$4.99",
            PRODUCT_YEARLY to "$2.99/yr",
            PRODUCT_MONTHLY to "$0.99/mo"
        )
    )
    val productPrices: StateFlow<Map<String, String>> = _productPrices.asStateFlow()

    private val productDetailsMap = mutableMapOf<String, ProductDetails>()

    @Volatile
    private var billingClient: BillingClient? = null

    private fun getOrCreateBillingClient(): BillingClient? {
        if (billingClient != null) return billingClient
        return try {
            instance ?: BillingClient.newBuilder(appContext)
                .setListener(this)
                .enablePendingPurchases(
                    PendingPurchasesParams.newBuilder()
                        .enableOneTimeProducts()
                        .build()
                )
                .build().also {
                    billingClient = it
                    instance = it
                }
        } catch (_: Throwable) {
            null
        }
    }

    init {
        try {
            startConnection()
        } catch (_: Throwable) {}
    }

    fun startConnection() {
        try {
            val client = getOrCreateBillingClient() ?: return
            client.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        queryProducts()
                        queryPurchases()
                    }
                }

                override fun onBillingServiceDisconnected() {
                }
            })
        } catch (_: Throwable) {}
    }

    private fun queryProducts() {
        try {
            val client = getOrCreateBillingClient() ?: return
            val inAppProductList = listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(PRODUCT_LIFETIME)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            )

            val subProductList = listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(PRODUCT_YEARLY)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(PRODUCT_MONTHLY)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )

            client.queryProductDetailsAsync(
                QueryProductDetailsParams.newBuilder().setProductList(inAppProductList).build()
            ) { billingResult, productDetailsResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    productDetailsResult.productDetailsList.forEach { details ->
                        productDetailsMap[details.productId] = details
                        val price = details.oneTimePurchaseOfferDetails?.formattedPrice
                        if (price != null) {
                            _productPrices.value = _productPrices.value + (details.productId to price)
                        }
                    }
                }
            }

            client.queryProductDetailsAsync(
                QueryProductDetailsParams.newBuilder().setProductList(subProductList).build()
            ) { billingResult, productDetailsResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    productDetailsResult.productDetailsList.forEach { details ->
                        productDetailsMap[details.productId] = details
                        val offerDetails = details.subscriptionOfferDetails?.firstOrNull()
                        val price = offerDetails?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice
                        if (price != null) {
                            _productPrices.value = _productPrices.value + (details.productId to price)
                        }
                    }
                }
            }
        } catch (_: Throwable) {}
    }

    fun launchBillingFlow(activity: Activity, productId: String) {
        try {
            val client = getOrCreateBillingClient() ?: return
            val details = productDetailsMap[productId] ?: return
            val productDetailsParamsList = if (details.productType == BillingClient.ProductType.SUBS) {
                val offerToken = details.subscriptionOfferDetails?.firstOrNull()?.offerToken
                if (offerToken.isNullOrEmpty()) return
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(details)
                        .setOfferToken(offerToken)
                        .build()
                )
            } else {
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(details)
                        .build()
                )
            }

            val flowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

            client.launchBillingFlow(activity, flowParams)
        } catch (_: Throwable) {}
    }

    fun queryPurchases(onComplete: ((Boolean) -> Unit)? = null) {
        try {
            val client = getOrCreateBillingClient()
            if (client == null) {
                onComplete?.invoke(false)
                return
            }
            if (!client.isReady) {
                startConnection()
                onComplete?.invoke(false)
                return
            }

            var hasPurchases = false
            var completedCount = 0

            fun checkDone() {
                completedCount++
                if (completedCount >= 2) {
                    onComplete?.invoke(hasPurchases)
                }
            }

            client.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
            ) { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    if (purchases.any { it.purchaseState == Purchase.PurchaseState.PURCHASED }) {
                        _isPremiumUnlocked.value = true
                        hasPurchases = true
                    }
                }
                checkDone()
            }

            client.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()
            ) { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    if (purchases.any { it.purchaseState == Purchase.PurchaseState.PURCHASED }) {
                        _isPremiumUnlocked.value = true
                        hasPurchases = true
                    }
                }
                checkDone()
            }
        } catch (_: Throwable) {
            onComplete?.invoke(false)
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        try {
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        _isPremiumUnlocked.value = true
                        if (!purchase.isAcknowledged) {
                            val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.purchaseToken)
                                .build()
                            getOrCreateBillingClient()?.acknowledgePurchase(acknowledgePurchaseParams) { }
                        }
                    }
                }
            }
        } catch (_: Throwable) {}
    }

    companion object {
        const val PRODUCT_LIFETIME = "premium_lifetime"
        const val PRODUCT_YEARLY = "premium_yearly"
        const val PRODUCT_MONTHLY = "premium_monthly"

        @Volatile
        private var instance: BillingClient? = null
    }
}
