package com.valentsytnik.retirementtimer

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingManager(private val context: Context) : PurchasesUpdatedListener {

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

    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder()
                .enableOneTimeProducts()
                .enablePrepaidPlans()
                .build()
        )
        .build()

    init {
        startConnection()
    }

    fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryProducts()
                    queryPurchases()
                }
            }

            override fun onBillingServiceDisconnected() {
                // Retry connection if needed
            }
        })
    }

    private fun queryProducts() {
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

        billingClient.queryProductDetailsAsync(
            QueryProductDetailsParams.newBuilder().setProductList(inAppProductList).build()
        ) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                productDetailsList.forEach { details ->
                    productDetailsMap[details.productId] = details
                    val price = details.oneTimePurchaseOfferDetails?.formattedPrice
                    if (price != null) {
                        _productPrices.value = _productPrices.value + (details.productId to price)
                    }
                }
            }
        }

        billingClient.queryProductDetailsAsync(
            QueryProductDetailsParams.newBuilder().setProductList(subProductList).build()
        ) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                productDetailsList.forEach { details ->
                    productDetailsMap[details.productId] = details
                    val offerDetails = details.subscriptionOfferDetails?.firstOrNull()
                    val price = offerDetails?.pricingPhases?.pricingPhaseList?.firstOrNull()?.formattedPrice
                    if (price != null) {
                        _productPrices.value = _productPrices.value + (details.productId to price)
                    }
                }
            }
        }
    }

    fun launchBillingFlow(activity: Activity, productId: String) {
        val details = productDetailsMap[productId] ?: return
        val productDetailsParamsList = if (details.productType == BillingClient.ProductType.SUBS) {
            val offerToken = details.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: ""
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

        billingClient.launchBillingFlow(activity, flowParams)
    }

    fun queryPurchases() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (purchases.any { it.purchaseState == Purchase.PurchaseState.PURCHASED }) {
                    _isPremiumUnlocked.value = true
                }
            }
        }

        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (purchases.any { it.purchaseState == Purchase.PurchaseState.PURCHASED }) {
                    _isPremiumUnlocked.value = true
                }
            }
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    _isPremiumUnlocked.value = true
                    if (!purchase.isAcknowledged) {
                        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()
                        billingClient.acknowledgePurchase(acknowledgePurchaseParams) { }
                    }
                }
            }
        }
    }

    companion object {
        const val PRODUCT_LIFETIME = "premium_lifetime"
        const val PRODUCT_YEARLY = "premium_yearly"
        const val PRODUCT_MONTHLY = "premium_monthly"
    }
}
