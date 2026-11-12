package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.CinzelFontFamily
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme

@Composable
fun TotalDaysHeaderPreviewPart1Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ВАРИАНТЫ 1 - 5 ОТОБРАЖЕНИЯ TOTAL: XX d",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Variant 1
        TotalHeaderCardWrapper(
            variantNumber = 1,
            title = "Вариант 1: Чистый золотой текст",
            description = "Playfair Display 15sp Bold: Total: 67 d"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Text("Total: 67 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
        }

        // Variant 2
        TotalHeaderCardWrapper(
            variantNumber = 2,
            title = "Вариант 2: Золотой капсульный бейдж",
            description = "Даты и общий итог внутри тонкой золотой капсулы"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.03f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text("total: 67 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 3
        TotalHeaderCardWrapper(
            variantNumber = 3,
            title = "Вариант 3: Двухцветный акцент (Cream + Gold)",
            description = "«Total:» в бежевом + «67 d» в золотом"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                val annotatedText = buildAnnotatedString {
                    withStyle(SpanStyle(fontFamily = PlayfairDisplay, fontSize = 14.5.sp, color = RetirementColors.Cream.copy(alpha = 0.8f))) {
                        append("Total: ")
                    }
                    withStyle(SpanStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold)) {
                        append("67 d")
                    }
                }
                Text(text = annotatedText)
            }
        }

        // Variant 4
        TotalHeaderCardWrapper(
            variantNumber = 4,
            title = "Вариант 4: Изумрудно-зеленый тег итогов",
            description = "В цвет окантовки календаря отпуска и бейджей месяцев"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF10B981).copy(alpha = 0.22f))
                        .border(1.dp, Color(0xFF10B981), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text("Total: 67 d", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                }
            }
        }

        // Variant 5
        TotalHeaderCardWrapper(
            variantNumber = 5,
            title = "Вариант 5: Тёмный стеклянный чип с золотой рамкой",
            description = "Чип с лёгким фоновым затемнением"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text("Total: 67 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun TotalDaysHeaderPreviewPart2Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ВАРИАНТЫ 6 - 10 ОТОБРАЖЕНИЯ TOTAL: XX d",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Variant 6
        TotalHeaderCardWrapper(
            variantNumber = 6,
            title = "Вариант 6: Современный лаконичный Sans-Serif",
            description = "FontFamily.SansSerif, 12.5sp Bold: TOTAL: 67 DAYS"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Text("TOTAL: 67 DAYS", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 0.5.sp))
            }
        }

        // Variant 7
        TotalHeaderCardWrapper(
            variantNumber = 7,
            title = "Вариант 7: Cormorant Garamond Italic",
            description = "Изящный курсив с засечками: Total: 67 d"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Text("Total: 67 d", style = TextStyle(fontFamily = CormorantGaramond, fontStyle = FontStyle.Italic, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
            }
        }

        // Variant 8
        TotalHeaderCardWrapper(
            variantNumber = 8,
            title = "Вариант 8: Римский античный Cinzel",
            description = "Cinzel Roman Imperial: TOTAL: 67 DAYS"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Text("TOTAL: 67 DAYS", style = TextStyle(fontFamily = CinzelFontFamily, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold, letterSpacing = 1.sp))
            }
        }

        // Variant 9
        TotalHeaderCardWrapper(
            variantNumber = 9,
            title = "Вариант 9: С иконкой календаря",
            description = "Иконка календаря + Total: 67 d"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(16.dp))
                    Text("Total: 67 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        // Variant 10
        TotalHeaderCardWrapper(
            variantNumber = 10,
            title = "Вариант 10: Золотое градиентное свечение",
            description = "Мягкий градиентный фоновый отблеск золота"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("VACATION DAYS", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold, letterSpacing = 1.5.sp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    RetirementColors.Gold.copy(alpha = 0.2f),
                                    Color.Transparent,
                                    RetirementColors.Gold.copy(alpha = 0.2f)
                                )
                            )
                        )
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Total: 67 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 13.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun TotalHeaderCardWrapper(
    variantNumber: Int,
    title: String,
    description: String,
    headerContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Вариант $variantNumber: $title",
            style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold)
        )
        Text(
            text = description,
            style = TextStyle(fontFamily = CormorantGaramond, fontSize = 12.sp, color = RetirementColors.Cream.copy(alpha = 0.7f)),
            modifier = Modifier.padding(bottom = 2.dp)
        )

        // Section Title Header Preview
        headerContent()

        // Mock Card 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("VACATION", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.22f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text("NOV-JAN", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                    }
                }
                Text("20.11.2026 – 09.01.2027", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Cream))
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(34.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.03f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(15.dp))
                        Text("35 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    }
                }
                Icon(Icons.Default.Delete, contentDescription = null, tint = RetirementColors.Cream.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
            }
        }

        // Mock Card 2
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("VACATION", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.22f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text("FEB-APR", style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 10.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF34D399)))
                    }
                }
                Text("25.02.2027 – 10.04.2027", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 14.sp, color = RetirementColors.Cream))
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(34.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.03f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = RetirementColors.Gold, modifier = Modifier.size(15.dp))
                        Text("32 d", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    }
                }
                Icon(Icons.Default.Delete, contentDescription = null, tint = RetirementColors.Cream.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1800, name = "Part 1: Total Header Variants 1 to 5")
@Composable
fun TotalDaysHeaderPreviewPart1() {
    RetirementTimerAppTheme {
        TotalDaysHeaderPreviewPart1Screen()
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 1800, name = "Part 2: Total Header Variants 6 to 10")
@Composable
fun TotalDaysHeaderPreviewPart2() {
    RetirementTimerAppTheme {
        TotalDaysHeaderPreviewPart2Screen()
    }
}
