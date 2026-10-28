package com.valentsytnik.retirementtimer

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
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.valentsytnik.retirementtimer.BuildConfig
import com.valentsytnik.retirementtimer.ui.theme.RetirementTimerAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            RetirementTimerAppTheme {
                MainNavigation()
            }
        }
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
            startDestination = "splash"
        ) {
            composable("splash") {
                SplashScreen(
                    onSplashFinished = {
                        navController.navigate("main") {
                            popUpTo("splash") { inclusive = true }
                        }
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

        if (currentRoute != "splash" && currentRoute != "premium" && !isPremium) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            ) {
                BottomAdBanner(height = 100.dp)
            }
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
