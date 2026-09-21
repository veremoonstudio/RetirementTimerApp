package com.example.retirementtimerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = RetirementColors.Gold,
    background = RetirementColors.Background,
    surface = RetirementColors.Background,
    onPrimary = Color.Black,
    onBackground = RetirementColors.Cream,
    onSurface = RetirementColors.Cream
)

@Composable
fun RetirementTimerAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
