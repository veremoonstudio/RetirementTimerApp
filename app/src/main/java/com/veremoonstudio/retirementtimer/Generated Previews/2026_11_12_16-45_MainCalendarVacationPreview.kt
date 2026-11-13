package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
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
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import com.veremoonstudio.retirementtimer.ui.theme.RetirementTimerAppTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MainCalendarWithVacationPreviewScreen() {
    val sampleRetirementDate = LocalDate.of(2026, 11, 11)
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
            text = "ТОЧНОЕ ПРЕВЬЮ КАЛЕНДАРЯ ПО СКРИНШОТУ",
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
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = RetirementColors.Background)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Top Header Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.25f))
                        .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Выберите дату",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 14.sp,
                            color = RetirementColors.Gold,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "11 нояб. 2026 г.",
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Normal,
                                color = RetirementColors.Gold
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Date",
                            tint = RetirementColors.Gold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                HorizontalDivider(color = Color.White.copy(alpha = 0.08f), thickness = 1.dp)

                // Month Body Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RetirementColors.Background)
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                ) {
                    // Month Title Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Ноябрь 2026 г.",
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select Year",
                                tint = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Previous Month",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(24.dp)
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Next Month",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Weekdays Header (П В С Ч П С В)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("П", "В", "С", "Ч", "П", "С", "В").forEach { day ->
                            Text(
                                text = day,
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    color = RetirementColors.Gold
                                ),
                                modifier = Modifier.width(40.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Month Days Grid
                    val month = YearMonth.of(2026, 11)
                    val firstDayOfWeek = month.atDay(1).dayOfWeek.value
                    val daysInMonth = month.lengthOfMonth()
                    val totalCells = (firstDayOfWeek - 1) + daysInMonth
                    val rows = (totalCells + 6) / 7

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .then(
                                                    when {
                                                        isRetirementDate -> Modifier.background(RetirementColors.Gold)
                                                        isInVacationRange && isWorkDay -> Modifier
                                                            .background(Color(0xFF10B981).copy(alpha = 0.06f))
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
                                                    fontWeight = if (isRetirementDate) FontWeight.Bold else FontWeight.Normal,
                                                    fontSize = 16.sp,
                                                    color = if (isRetirementDate) Color(0xFF111315) else Color.White
                                                )
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.size(40.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                // Footer Action Buttons (Dedicated Panel with contrasting container background)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2B2930))
                        .padding(start = 24.dp, end = 24.dp, top = 0.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Text(
                        text = "OK",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = RetirementColors.Gold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 410, heightDp = 640, name = "Main Calendar With Vacation Preview")
@Composable
fun MainCalendarWithVacationPreview() {
    RetirementTimerAppTheme {
        MainCalendarWithVacationPreviewScreen()
    }
}
