package com.veremoonstudio.retirementtimer

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import android.content.pm.ActivityInfo
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme
import kotlinx.coroutines.delay

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } catch (_: Throwable) {}
        initUmpConsentAndAds()
        try {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
                navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
            )
            hideSystemNavigationBars()
        } catch (_: Throwable) {}
        setContent {
            RetirementTimerAppTheme {
                MainNavigation()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemNavigationBars()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemNavigationBars()
        updateWidget()
    }

    override fun onPause() {
        super.onPause()
        updateWidget()
    }

    private fun hideSystemNavigationBars() {
        try {
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        } catch (_: Throwable) {}
    }

    private fun initUmpConsentAndAds() {
        try {
            val reqConfig = MobileAds.getRequestConfiguration().toBuilder()
                .setTestDeviceIds(listOf(AdRequest.DEVICE_ID_EMULATOR, "0B32F41C2E97D40CB883361B199BE7F5"))
                .build()
            MobileAds.setRequestConfiguration(reqConfig)
        } catch (_: Throwable) {}

        try {
            val params = ConsentRequestParameters.Builder().build()
            val consentInformation = UserMessagingPlatform.getConsentInformation(this)
            consentInformation.requestConsentInfoUpdate(
                this,
                params,
                {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(this) { _ ->
                        if (consentInformation.canRequestAds()) {
                            try {
                                MobileAds.initialize(this) {}
                            } catch (_: Throwable) {}
                        }
                    }
                },
                { _ ->
                    if (consentInformation.canRequestAds()) {
                        try {
                            MobileAds.initialize(this) {}
                        } catch (_: Throwable) {}
                    }
                }
            )
        } catch (_: Throwable) {
            try {
                MobileAds.initialize(this) {}
            } catch (_: Throwable) {}
        }
    }

    private fun updateWidget() {
        try {
            RetirementWidgetReceiver.scheduleNextMinuteAlarm(this)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    RetirementGlanceWidget().updateAll(applicationContext)
                } catch (_: Throwable) {}
            }
        } catch (_: Throwable) {}
    }
}

@Composable
fun MainNavigation(
    workTimeViewModel: WorkTimeViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isPremiumUnlocked by workTimeViewModel.billingManager.isPremiumUnlocked.collectAsState()
    val isPremium = BuildConfig.IS_PREMIUM || isPremiumUnlocked

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "splash",
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            popEnterTransition = { fadeIn(animationSpec = tween(300)) },
            popExitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            composable("splash") {
                SplashScreen(
                    onSplashFinished = {
                        try {
                            navController.navigate("main") {
                                popUpTo("splash") { inclusive = true }
                            }
                        } catch (_: Throwable) {}
                    }
                )
            }
            composable("main") {
                WorkTimeScreen(
                    viewModel = workTimeViewModel,
                    onOpenMenu = { navController.navigate("settings") },
                    onNavigateToPremium = { navController.navigate("premium") },
                    onNavigateToBackground = { navController.navigate("background") }
                )
            }
            composable("settings") {
                SettingsScreen(
                    viewModel = workTimeViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("premium") {
                PremiumScreen(
                    viewModel = workTimeViewModel,
                    onClose = { navController.popBackStack() }
                )
            }
            composable("background") {
                BackgroundScreen(
                    viewModel = workTimeViewModel,
                    onBack = { navController.popBackStack() },
                    onNavigateToPremium = { navController.navigate("premium") }
                )
            }
        }

        val showBanner = currentRoute in listOf("main", "settings", "background")
        AnimatedVisibility(
            visible = showBanner && !isPremium,
            enter = fadeIn(animationSpec = tween(300)) + slideInVertically(animationSpec = tween(300)) { it },
            exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(animationSpec = tween(300)) { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomAdBanner(height = 100.dp)
        }
    }
}

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color(0xFF111315)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Splash Screen",
            modifier = Modifier
                .size(240.dp)
                .padding(16.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onSplashFinished = {})
}
