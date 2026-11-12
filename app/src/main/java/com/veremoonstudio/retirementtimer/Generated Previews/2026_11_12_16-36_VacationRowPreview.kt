package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
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
fun VacationRowPreviewScreen() {
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
            text = "10 ВАРИАНТОВ ОТОБРАЖЕНИЯ СТРОКИ ОТПУСКА",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.5.sp
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Variant 1
        VacationRowOptionCard(
            title = "Вариант 1: Полные даты в одну строку (Текущий)",
            description = "Playfair Display 13sp: Vacation 13.11.2026 - 19.11.2026"
        ) {
            VacationRowContainer {
                Text(
                    text = "Vacation 13.11.2026 - 19.11.2026",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RetirementColors.Cream
                    ),
                    modifier = Modifier.weight(1f, fill = false)
                )

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 2
        VacationRowOptionCard(
            title = "Вариант 2: Золотое выделение диапазона дат",
            description = "Двухцветный текст: «Vacation» в бежевом + «13.11 – 19.11.2026» в золотом"
        ) {
            VacationRowContainer {
                val annotatedText = buildAnnotatedString {
                    withStyle(SpanStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Cream)) {
                        append("Vacation ")
                    }
                    withStyle(SpanStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold)) {
                        append("13.11 – 19.11.2026")
                    }
                }
                Text(text = annotatedText, modifier = Modifier.weight(1f, fill = false))

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 3
        VacationRowOptionCard(
            title = "Вариант 3: Современный лаконичный (Sans-Serif)",
            description = "Лаконичный шрифт без засечек: Vacation 13.11 → 19.11.2026"
        ) {
            VacationRowContainer {
                Text(
                    text = "Vacation 13.11 → 19.11.2026",
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = RetirementColors.Cream
                    ),
                    modifier = Modifier.weight(1f, fill = false)
                )

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 4
        VacationRowOptionCard(
            title = "Вариант 4: Изящный с названиями месяцев",
            description = "Cormorant Garamond 15sp: Vacation: 13 Nov – 19 Nov 2026"
        ) {
            VacationRowContainer {
                Text(
                    text = "Vacation: 13 Nov – 19 Nov 2026",
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RetirementColors.Cream
                    ),
                    modifier = Modifier.weight(1f, fill = false)
                )

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 5
        VacationRowOptionCard(
            title = "Вариант 5: Двухстрочный премиум бейдж",
            description = "Заголовок «VACATION DAYS» золотом + дата на второй строке"
        ) {
            VacationRowContainer {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp), modifier = Modifier.weight(1f, fill = false)) {
                    Text(
                        text = "VACATION DAYS",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "13.11.2026 – 19.11.2026",
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = RetirementColors.Cream
                        )
                    )
                }

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 6
        VacationRowOptionCard(
            title = "Вариант 6: Изумрудный акцент календаря (Emerald Tag)",
            description = "Зелёный мини-бейдж «VACATION» + лаконичные даты"
        ) {
            VacationRowContainer {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f, fill = false)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF10B981).copy(alpha = 0.25f))
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "VACATION",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF34D399)
                            )
                        )
                    }
                    Text(
                        text = "13.11 – 19.11.2026",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = RetirementColors.Cream
                        )
                    )
                }

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 7
        VacationRowOptionCard(
            title = "Вариант 7: Сокращённый компактный год (DD.MM – DD.MM.YY)",
            description = "Короткие компактные даты: Vacation 13.11 – 19.11.26"
        ) {
            VacationRowContainer {
                Text(
                    text = "Vacation 13.11 – 19.11.26",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RetirementColors.Cream
                    ),
                    modifier = Modifier.weight(1f, fill = false)
                )

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 8
        VacationRowOptionCard(
            title = "Вариант 8: Римский монументальный (Cinzel Roman)",
            description = "Античный римский стиль: VACATION: 13.11 – 19.11.2026"
        ) {
            VacationRowContainer {
                Text(
                    text = "VACATION: 13.11 – 19.11.2026",
                    style = TextStyle(
                        fontFamily = CinzelFontFamily,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = RetirementColors.Gold,
                        letterSpacing = 0.5.sp
                    ),
                    modifier = Modifier.weight(1f, fill = false)
                )

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 9
        VacationRowOptionCard(
            title = "Вариант 9: Золотой капсульный чип (Pill Badge)",
            description = "Даты внутри изящной золотой рамки-капсулы"
        ) {
            VacationRowContainer {
                Text(
                    text = "Vacation",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RetirementColors.Cream
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.03f))
                        .border(1.dp, RetirementColors.Gold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "13.11 – 19.11.2026",
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold
                        )
                    )
                }

                VacationButtonBox(text = "5 d")
            }
        }

        // Variant 10
        VacationRowOptionCard(
            title = "Вариант 10: Минималистичный стрелочный акцент",
            description = "Лаконичный вид: Vacation 13.11.26 ➔ 19.11.26"
        ) {
            VacationRowContainer {
                Text(
                    text = "Vacation 13.11.26 ➔ 19.11.26",
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = RetirementColors.Gold
                    ),
                    modifier = Modifier.weight(1f, fill = false)
                )

                VacationButtonBox(text = "5 d")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun VacationRowOptionCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(12.dp))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .padding(14.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = RetirementColors.Gold
            )
        )
        Text(
            text = description,
            style = TextStyle(
                fontFamily = CormorantGaramond,
                fontSize = 12.sp,
                color = RetirementColors.Cream.copy(alpha = 0.65f)
            ),
            modifier = Modifier.padding(bottom = 6.dp)
        )
        content()
    }
}

@Composable
private fun VacationRowContainer(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content
    )
}

@Composable
private fun VacationButtonBox(text: String) {
    Box(
        modifier = Modifier
            .width(85.dp)
            .height(38.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = null,
                tint = RetirementColors.Gold,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = PlayfairDisplay,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = RetirementColors.Gold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 2200, name = "10 Vacation Row Options Preview")
@Composable
fun VacationRowPreview() {
    RetirementTimerAppTheme {
        VacationRowPreviewScreen()
    }
}
