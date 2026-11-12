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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.CinzelFontFamily
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun DateFontPreviewScreen() {
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
            text = "ПРЕВО ВАРИАНТОВ ШРИФТА ДАТЫ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.5.sp
            ),
            modifier = Modifier.padding(vertical = 12.dp)
        )

        // Variant 1
        DateFontOptionCard(
            variantNumber = 1,
            title = "System Sans-Serif (Текущий)",
            description = "FontFamily.SansSerif, Medium"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 19.sp
            )
        }

        // Variant 2
        DateFontOptionCard(
            variantNumber = 2,
            title = "Playfair Display SemiBold",
            description = "Премиальный шрифт заголовков приложения"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.sp
            )
        }

        // Variant 3
        DateFontOptionCard(
            variantNumber = 3,
            title = "Cormorant Garamond Regular",
            description = "Классическая элегантная антиква"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = CormorantGaramond,
                fontWeight = FontWeight.Medium,
                fontSize = 21.sp
            )
        }

        // Variant 4
        DateFontOptionCard(
            variantNumber = 4,
            title = "Cormorant Garamond Italic",
            description = "Изящный курсив с наклонными буквами"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = CormorantGaramond,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 21.sp
            )
        }

        // Variant 5
        DateFontOptionCard(
            variantNumber = 5,
            title = "Cinzel Roman Imperial",
            description = "Римский монументальный шрифт"
        ) {
            Text(
                text = "13 НОЯБ. - 15 НОЯБ. 2026 Г.",
                color = RetirementColors.Gold,
                fontFamily = CinzelFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            )
        }

        // Variant 6
        DateFontOptionCard(
            variantNumber = 6,
            title = "System Serif",
            description = "FontFamily.Serif, Традиционный шрифт с засечками"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                fontSize = 19.sp
            )
        }

        // Variant 7
        DateFontOptionCard(
            variantNumber = 7,
            title = "System Monospace Digital",
            description = "FontFamily.Monospace, Строгая цифровая точность"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
        }

        // Variant 8
        DateFontOptionCard(
            variantNumber = 8,
            title = "Playfair Display Bold",
            description = "Плотный жирный Playfair Display"
        ) {
            Text(
                text = "13 нояб. - 15 нояб. 2026 г.",
                color = RetirementColors.Gold,
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DateFontOptionCard(
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
                text = "Вариант $variantNumber: $title",
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
                .height(52.dp)
                .background(Color(0xFF212121), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1100, name = "Date Font Options Preview")
@Composable
fun DateFontPreview() {
    RetirementTimerAppTheme {
        DateFontPreviewScreen()
    }
}
