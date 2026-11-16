@file:JvmName("WeekdayNameLengthOptionsPreviewDotFormat")
package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
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
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeekdayNameLengthOptionsPreviewScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RetirementColors.Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "ПРЕВЬЮ: 2 И 3 БУКВЫ В НАЗВАНИЯХ ДНЕЙ НЕДЕЛИ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = RetirementColors.Gold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )
        )

        // 2-Letter Weekday Preview
        CalendarHeaderPreviewCard(
            title = "Вариант 1: Названия из 2 букв (Mo Tu We Th Fr Sa Su)",
            weekdays = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
        )

        // 3-Letter Weekday Preview
        CalendarHeaderPreviewCard(
            title = "Вариант 2: Названия из 3 букв (Mon Tue Wed Thu Fri Sat Sun)",
            weekdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        )
    }
}

@Composable
private fun CalendarHeaderPreviewCard(title: String, weekdays: List<String>) {
    val sampleDate = LocalDate.of(2026, 11, 13)
    val localeUs = Locale.US

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.04f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Top Header Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.25f))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Select Date",
                        style = TextStyle(
                            fontFamily = PlayfairDisplay,
                            fontSize = 13.sp,
                            color = RetirementColors.Gold,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sampleDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy", localeUs)),
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Normal,
                                color = RetirementColors.Gold
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Date",
                            tint = RetirementColors.Gold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                HorizontalDivider(color = Color.White.copy(alpha = 0.08f), thickness = 1.dp)

                // Month Bar & Weekday Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RetirementColors.Background)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "November 2026",
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // WEEKDAYS HEADER (2 OR 3 LETTERS)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        weekdays.forEach { day ->
                            Text(
                                text = day,
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = if (weekdays.first().length == 3) 12.sp else 13.sp,
                                    color = RetirementColors.Gold
                                ),
                                modifier = Modifier.width(38.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Days Grid Snippet (Sample row)
                    val month = YearMonth.of(2026, 11)
                    val firstDayOfWeek = month.atDay(1).dayOfWeek.value
                    val daysInMonth = month.lengthOfMonth()
                    val totalCells = (firstDayOfWeek - 1) + daysInMonth
                    val rows = (totalCells + 6) / 7

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        for (row in 0 until minOf(rows, 3)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (col in 0 until 7) {
                                    val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                                    if (dayNumber in 1..daysInMonth) {
                                        val isSelected = dayNumber == 13
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(CircleShape)
                                                .background(if (isSelected) RetirementColors.Gold else Color.Transparent)
                                                .border(if (isSelected) 0.dp else 1.dp, Color.Transparent, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = dayNumber.toString(),
                                                style = TextStyle(
                                                    fontFamily = FontFamily.SansSerif,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    fontSize = 15.sp,
                                                    color = if (isSelected) Color(0xFF111315) else Color.White
                                                )
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.size(38.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// INDIVIDUAL PREVIEWS
@Preview(showBackground = true, widthDp = 380, heightDp = 310, name = "1: 2-Letter Weekdays (Mo Tu We...)")
@Composable
fun Preview2LetterWeekdays() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
            CalendarHeaderPreviewCard(
                title = "Вариант 1: Названия из 2 букв (Mo Tu We Th Fr Sa Su)",
                weekdays = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 310, name = "2: 3-Letter Weekdays (Mon Tue Wed...)")
@Composable
fun Preview3LetterWeekdays() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(RetirementColors.Background).padding(16.dp)) {
            CalendarHeaderPreviewCard(
                title = "Вариант 2: Названия из 3 букв (Mon Tue Wed Thu Fri Sat Sun)",
                weekdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            )
        }
    }
}
