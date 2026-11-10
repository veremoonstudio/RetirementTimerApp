package com.veremoonstudio.retirementtimer

import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors

@Composable
fun BottomAdBanner(height: Dp = 100.dp) {
    var isAdLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        if (!isAdLoaded) {
            Box(
                modifier = Modifier
                    .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.advertisement),
                    color = RetirementColors.Gold.copy(alpha = 0.8f),
                    fontFamily = CormorantGaramond,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    letterSpacing = 2.sp
                )
            }
        }

        AndroidView(
            factory = { context ->
                val frameLayout = FrameLayout(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                try {
                    val displayMetrics = context.resources.displayMetrics
                    val widthPixels = displayMetrics.widthPixels
                    val density = displayMetrics.density
                    val rawWidth = if (density > 0) (widthPixels / density).toInt() else 320
                    val adWidth = if (rawWidth > 0) rawWidth else 320
                    val adSize = try {
                        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
                    } catch (_: Throwable) {
                        AdSize.BANNER
                    }

                    val adView = AdView(context).apply {
                        setAdSize(adSize)
                        // Official AdMob test banner ad unit ID
                        adUnitId = "ca-app-pub-3940256099942544/6300978111"
                        adListener = object : AdListener() {
                            override fun onAdLoaded() {
                                Log.d("AdBanner", "AdMob banner loaded successfully")
                                isAdLoaded = true
                            }
                            override fun onAdFailedToLoad(error: LoadAdError) {
                                Log.e("AdBanner", "AdMob banner failed to load: ${error.message} (code: ${error.code})")
                                isAdLoaded = false
                            }
                        }
                        loadAd(AdRequest.Builder().build())
                    }
                    val lp = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = Gravity.CENTER
                    }
                    frameLayout.addView(adView, lp)
                } catch (t: Throwable) {
                    Log.e("AdBanner", "Error initializing AdView", t)
                }
                frameLayout
            },
            onRelease = { frameLayout ->
                try {
                    for (i in 0 until frameLayout.childCount) {
                        val child = frameLayout.getChildAt(i)
                        if (child is AdView) {
                            child.destroy()
                        }
                    }
                    frameLayout.removeAllViews()
                } catch (t: Throwable) {
                    Log.e("AdBanner", "Error destroying AdView", t)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
