package com.example.retirementtimerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LuxColorScheme = darkColorScheme(
    primary = LuxGold,
    onPrimary = LuxBlack,
    secondary = LuxSecondaryGold,
    background = LuxBlack,
    surface = IconGradStart,
    onBackground = LuxBeige,
    onSurface = LuxBeige
)

@Composable
fun RetirementTimerAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LuxColorScheme,
        typography = Typography,
        content = content
    )
}
