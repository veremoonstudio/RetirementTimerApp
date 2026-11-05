package com.veremoonstudio.retirementtimer

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veremoonstudio.retirementtimer.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(
    onClose: () -> Unit,
    viewModel: WorkTimeViewModel = viewModel()
) {
    var selectedOption by remember { mutableStateOf(BillingManager.PRODUCT_YEARLY) }
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val productPrices by viewModel.billingManager.productPrices.collectAsState()

    MarbleBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = RetirementColors.Gold)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.MilitaryTech,
                        contentDescription = null,
                        tint = RetirementColors.Gold,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.get_premium_title),
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = RetirementColors.Cream,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.get_premium_subtitle),
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontSize = 18.sp,
                            color = RetirementColors.Gold.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    )
                }

                // Options
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PremiumOption(
                        title = stringResource(R.string.lifetime),
                        price = productPrices[BillingManager.PRODUCT_LIFETIME] ?: "$4.99",
                        isSelected = selectedOption == BillingManager.PRODUCT_LIFETIME,
                        onClick = { selectedOption = BillingManager.PRODUCT_LIFETIME }
                    )
                    PremiumOption(
                        title = stringResource(R.string.yearly),
                        price = productPrices[BillingManager.PRODUCT_YEARLY] ?: "$2.99/yr",
                        isSelected = selectedOption == BillingManager.PRODUCT_YEARLY,
                        onClick = { selectedOption = BillingManager.PRODUCT_YEARLY }
                    )
                    PremiumOption(
                        title = stringResource(R.string.monthly),
                        price = productPrices[BillingManager.PRODUCT_MONTHLY] ?: "$0.99/mo",
                        isSelected = selectedOption == BillingManager.PRODUCT_MONTHLY,
                        onClick = { selectedOption = BillingManager.PRODUCT_MONTHLY }
                    )
                }

                // Footer
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = {
                            val activity = context as? Activity
                            if (activity != null) {
                                viewModel.billingManager.launchBillingFlow(activity, selectedOption)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(GoldPrimary, GoldDeep)),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            stringResource(R.string.continue_button),
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = RetirementColors.Background
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val termsUrl = stringResource(R.string.terms_url)
                    val privacyUrl = stringResource(R.string.privacy_policy_url)
                    val context = LocalContext.current
                    val msgRestoring = stringResource(R.string.restoring_purchases)
                    val msgRestored = stringResource(R.string.purchases_restored)
                    val msgNoPurchases = stringResource(R.string.no_purchases_found)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        FooterLink(stringResource(R.string.restore_purchases)) {
                            Toast.makeText(context, msgRestoring, Toast.LENGTH_SHORT).show()
                            viewModel.billingManager.queryPurchases { found ->
                                Handler(Looper.getMainLooper()).post {
                                    if (found) {
                                        Toast.makeText(context, msgRestored, Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, msgNoPurchases, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                        Text("•", color = RetirementColors.AdTextColor)
                        FooterLink(stringResource(R.string.terms_link)) {
                            try {
                                uriHandler.openUri(termsUrl)
                            } catch (_: Throwable) {}
                        }
                        Text("•", color = RetirementColors.AdTextColor)
                        FooterLink(stringResource(R.string.privacy_policy_link)) {
                            try {
                                uriHandler.openUri(privacyUrl)
                            } catch (_: Throwable) {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumOption(title: String, price: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color.White.copy(alpha = 0.08f) else Color.White.copy(alpha = 0.03f))
            .border(
                width = 1.dp,
                color = if (isSelected) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isSelected) RetirementColors.Gold else RetirementColors.Cream
                    )
                )
                Text(
                    text = price,
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontSize = 14.sp,
                        color = RetirementColors.Gold.copy(alpha = 0.6f)
                    )
                )
            }
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = RetirementColors.Gold,
                    unselectedColor = RetirementColors.Gold.copy(alpha = 0.4f)
                )
            )
        }
    }
}

@Composable
fun FooterLink(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = CormorantGaramond,
            fontSize = 13.sp,
            color = RetirementColors.Gold
        ),
        modifier = Modifier.clickable { onClick() }
    )
}
