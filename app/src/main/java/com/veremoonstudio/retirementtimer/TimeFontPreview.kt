package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors

private val CinzelFont = FontFamily(
    Font(R.font.cinzel, FontWeight.Normal)
)

@Composable
fun TimeFontPreviewScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "10 TYPOGRAPHY VARIANTS FOR 6:00",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        // Variant 1
        FontOptionCard(
            variantNumber = 1,
            title = "Standard System Default",
            description = "FontFamily.Default, Medium"
        ) {
            Text(
                text = "AT 6:00 PM",
                color = RetirementColors.Cream,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp,
                letterSpacing = 1.sp
            )
        }

        // Variant 2
        FontOptionCard(
            variantNumber = 2,
            title = "Playfair Display SemiBold",
            description = "Main app display font, SemiBold"
        ) {
            Text(
                text = "AT 6:00 PM",
                color = RetirementColors.Cream,
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                letterSpacing = 1.sp
            )
        }

        // Variant 3
        FontOptionCard(
            variantNumber = 3,
            title = "Cormorant Garamond Italic",
            description = "Classic serif italic, lowercase"
        ) {
            Text(
                text = "at 6:00 pm",
                color = RetirementColors.Cream,
                fontFamily = CormorantGaramond,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 1.5.sp
            )
        }

        // Variant 4
        FontOptionCard(
            variantNumber = 4,
            title = "Cinzel Classical Roman",
            description = "Imperial Roman font style"
        ) {
            Text(
                text = "AT 6:00 PM",
                color = RetirementColors.Cream,
                fontFamily = CinzelFont,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                letterSpacing = 2.sp
            )
        }

        // Variant 5
        FontOptionCard(
            variantNumber = 5,
            title = "System Serif",
            description = "FontFamily.Serif, Normal"
        ) {
            Text(
                text = "AT 6:00 PM",
                color = RetirementColors.Cream,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                letterSpacing = 1.5.sp
            )
        }

        // Variant 6
        FontOptionCard(
            variantNumber = 6,
            title = "Monospace Digital Precision",
            description = "FontFamily.Monospace, SemiBold"
        ) {
            Text(
                text = "AT 06:00 PM",
                color = RetirementColors.Cream,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            )
        }

        // Variant 7
        FontOptionCard(
            variantNumber = 7,
            title = "Mixed Elegance (Cormorant + Playfair)",
            description = "AT in Italic + 6:00 PM in Playfair"
        ) {
            val text = buildAnnotatedString {
                withStyle(SpanStyle(fontFamily = CormorantGaramond, fontStyle = FontStyle.Italic, fontSize = 18.sp)) {
                    append("AT ")
                }
                withStyle(SpanStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)) {
                    append("6:00 PM")
                }
            }
            Text(text = text, color = RetirementColors.Cream)
        }

        // Variant 8
        FontOptionCard(
            variantNumber = 8,
            title = "Gold Accent Italic",
            description = "Gold color + Cormorant Italic"
        ) {
            Text(
                text = "at 6:00 pm",
                color = RetirementColors.Gold,
                fontFamily = CormorantGaramond,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 1.5.sp
            )
        }

        // Variant 9
        FontOptionCard(
            variantNumber = 9,
            title = "Wide Spaced Sans-Serif",
            description = "FontFamily.SansSerif with 4.sp tracking"
        ) {
            Text(
                text = "AT  6:00  PM",
                color = RetirementColors.Cream,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                letterSpacing = 4.sp
            )
        }

        // Variant 10
        FontOptionCard(
            variantNumber = 10,
            title = "Playfair Display Bold",
            description = "Bold Playfair Display font"
        ) {
            Text(
                text = "AT 6:00 PM",
                color = RetirementColors.Cream,
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                letterSpacing = 1.5.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun FontOptionCard(
    variantNumber: Int,
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(12.dp))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Variant $variantNumber: $title",
                style = TextStyle(
                    fontFamily = PlayfairDisplay,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = RetirementColors.Gold
                )
            )
        }
        Text(
            text = description,
            style = TextStyle(
                fontFamily = CormorantGaramond,
                fontSize = 12.sp,
                color = RetirementColors.Cream.copy(alpha = 0.6f)
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1400)
@Composable
fun TimeFontPreview() {
    TimeFontPreviewScreen()
}
