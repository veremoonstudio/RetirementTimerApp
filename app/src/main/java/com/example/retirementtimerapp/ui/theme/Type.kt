package com.example.retirementtimerapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Defined in RetirementTimerScreen.kt
val PlayfairDisplay = FontFamily.Serif
val CormorantGaramond = FontFamily.Serif

// Old names for compatibility if any
val PlayfairFontFamily = PlayfairDisplay
val CormorantFontFamily = CormorantGaramond
val InterFontFamily = FontFamily.SansSerif
val FrauncesFontFamily = FontFamily.Serif

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)
