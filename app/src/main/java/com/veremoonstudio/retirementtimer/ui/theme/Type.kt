package com.veremoonstudio.retirementtimer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.R

val PlayfairDisplay = FontFamily(
    Font(R.font.playfair_display_semibold, FontWeight.SemiBold),
    Font(R.font.playfair_display_semibold, FontWeight.Bold),
    Font(R.font.playfair_display_semibold, FontWeight.Normal)
)

val CormorantGaramond = FontFamily(
    Font(R.font.cormorant_garamond_medium, FontWeight.Medium),
    Font(R.font.cormorant_garamond_semibold, FontWeight.SemiBold),
    Font(R.font.cormorant_garamond_semibold, FontWeight.Bold),
    Font(R.font.cormorant_garamond_medium, FontWeight.Normal),
    Font(R.font.cormorant_garamond_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.cormorant_garamond_medium_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.cormorant_garamond_medium_italic, FontWeight.Normal, FontStyle.Italic)
)

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
