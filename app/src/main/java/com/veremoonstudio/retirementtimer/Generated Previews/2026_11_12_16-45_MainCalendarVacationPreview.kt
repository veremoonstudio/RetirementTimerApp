package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MainCalendarWithVacationPreviewScreen() {
    val sampleRetirementDate = LocalDate.of(2026, 11, 28)
    val sampleVacationStart = LocalDate.of(2026, 11, 13)
    val sampleVacationEnd = LocalDate.of(2026, 11, 22)
    val workDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    val holidays = listOf(Holiday("Holiday", 11, 18, true))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ПРЕВО КАЛЕНДАРЯ НА ГЛАВНОМ ЭКРАНЕ С ДНЯМИ ОТПУСКА",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Title & Headline
                Text(
                    text = "Выберите дату выхода на пенсию",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 14.sp, color = RetirementColors.Gold, fontWeight = FontWeight.Medium)
                )

                Text(
                    text = "28 нояб. 2026 г.",
                    style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = RetirementColors.Gold)
                )

                HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)

                // Legend
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. Retirement Date (Solid Gold)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(RetirementColors.Gold)
                            )
                            Text("Пенсия", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 12.5.sp, color = RetirementColors.Cream))
                        }

                        // 2. Vacation Work Days (Green Ring)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981).copy(alpha = 0.25f))
                                    .border(1.dp, Color(0xFF10B981), CircleShape)
                            )
                            Text("Отпуск (раб.)", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 12.5.sp, color = RetirementColors.Cream))
                        }

                        // 3. Vacation Non-Work Days (White Ring)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.White.copy(alpha = 0.45f), CircleShape)
                            )
                            Text("Отпуск (вых.)", style = TextStyle(fontFamily = CormorantGaramond, fontSize = 12.5.sp, color = RetirementColors.Cream))
                        }
                    }
                }

                // Days Header (П В С Ч П С В)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("П", "В", "С", "Ч", "П", "С", "В").forEach { day ->
                        Text(
                            text = day,
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = RetirementColors.Gold
                            ),
                            modifier = Modifier.width(36.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Month Title
                Text(
                    text = "Ноябрь 2026 г.",
                    style = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold),
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )

                // Month Grid
                val month = YearMonth.of(2026, 11)
                val firstDayOfWeek = month.atDay(1).dayOfWeek.value // Mon = 1
                val daysInMonth = month.lengthOfMonth()
                val totalCells = (firstDayOfWeek - 1) + daysInMonth
                val rows = (totalCells + 6) / 7

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (col in 0 until 7) {
                                val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                                if (dayNumber in 1..daysInMonth) {
                                    val date = month.atDay(dayNumber)
                                    val isRetirementDate = date == sampleRetirementDate
                                    val isInVacationRange = !date.isBefore(sampleVacationStart) && !date.isAfter(sampleVacationEnd)
                                    val isWorkDay = WorkTimeCalculator.isWorkDay(date, workDays, holidays)

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .then(
                                                when {
                                                    isRetirementDate -> Modifier.background(RetirementColors.Gold)
                                                    isInVacationRange && isWorkDay -> Modifier
                                                        .background(Color(0xFF10B981).copy(alpha = 0.25f))
                                                        .border(1.dp, Color(0xFF10B981), CircleShape)
                                                    isInVacationRange && !isWorkDay -> Modifier
                                                        .border(1.dp, Color.White.copy(alpha = 0.45f), CircleShape)
                                                    else -> Modifier
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayNumber.toString(),
                                            style = TextStyle(
                                                fontFamily = FontFamily.SansSerif,
                                                fontWeight = if (isRetirementDate || isInVacationRange) FontWeight.Bold else FontWeight.Normal,
                                                fontSize = 15.sp,
                                                color = if (isRetirementDate) Color(0xFF111315) else Color.White
                                            )
                                        )
                                    }
                                } else {
                                    Spacer(modifier = Modifier.size(36.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Footer Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("Отмена", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, color = RetirementColors.Cream))
                        Text("ОК", style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 375, heightDp = 640, name = "Main Calendar With Vacation Preview")
@Composable
fun MainCalendarWithVacationPreview() {
    RetirementTimerAppTheme {
        MainCalendarWithVacationPreviewScreen()
    }
}
