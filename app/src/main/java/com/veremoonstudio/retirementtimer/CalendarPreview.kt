package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.*
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarPreviewScreen() {
    val sampleRetirementDate = LocalDate.of(2026, 11, 12)
    val sampleVacationDays = setOf(
        LocalDate.of(2026, 11, 15),
        LocalDate.of(2026, 11, 16),
        LocalDate.of(2026, 11, 17),
        LocalDate.of(2026, 11, 18),
        LocalDate.of(2026, 11, 19),
        LocalDate.of(2026, 11, 20)
    )
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111315))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "ДНИ ОТПУСКА В ТЕКУЩЕМ КАЛЕНДАРЕ",
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = RetirementColors.Gold,
                letterSpacing = 2.sp
            )
        )

        Text(
            text = "Твой текущий календарь Material DatePicker с зелёной окантовкой для дней отпуска:",
            style = TextStyle(
                fontFamily = CormorantGaramond,
                fontSize = 15.sp,
                color = RetirementColors.Cream.copy(alpha = 0.85f)
            )
        )

        // Option 1
        Text(
            text = "Вариант 1: Изумрудно-зелёная окантовка (1.5 dp)",
            style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RetirementColors.Gold)
        )
        MaterialDatePickerDialogCustom(
            selectedDate = sampleRetirementDate,
            vacationDays = sampleVacationDays,
            greenBorderColor = Color(0xFF10B981),
            borderWidth = 1.5.dp,
            hasTint = false
        )

        // Option 2
        Text(
            text = "Вариант 2: Чёткое мятно-зелёное кольцо (2 dp)",
            style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RetirementColors.Gold)
        )
        MaterialDatePickerDialogCustom(
            selectedDate = sampleRetirementDate,
            vacationDays = sampleVacationDays,
            greenBorderColor = Color(0xFF34D399),
            borderWidth = 2.dp,
            hasTint = false
        )

        // Option 3
        Text(
            text = "Вариант 3: Зелёная окантовка + лёгкий полупрозрачный зеленый фон",
            style = TextStyle(fontFamily = PlayfairDisplay, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RetirementColors.Gold)
        )
        MaterialDatePickerDialogCustom(
            selectedDate = sampleRetirementDate,
            vacationDays = sampleVacationDays,
            greenBorderColor = Color(0xFF10B981),
            borderWidth = 1.5.dp,
            hasTint = true
        )
    }
}

// -------------------------------------------------------------
// EXACT MATERIAL 3 DATE PICKER DIALOG LAYOUT (MATCHING SCREENSHOT)
// -------------------------------------------------------------
@Composable
fun MaterialDatePickerDialogCustom(
    selectedDate: LocalDate,
    vacationDays: Set<LocalDate>,
    greenBorderColor: Color,
    borderWidth: Dp,
    hasTint: Boolean = false
) {
    val month = YearMonth.of(2026, 11)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Top Label
            Text(
                text = "Выберите дату",
                style = TextStyle(
                    fontFamily = CormorantGaramond,
                    fontSize = 14.sp,
                    color = Color(0xFFD4B26F),
                    fontWeight = FontWeight.Medium
                )
            )

            // Headline Date & Edit Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "12 нояб. 2026 г.",
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color(0xFFE5C78B)
                    )
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color(0xFFD4B26F),
                    modifier = Modifier.size(20.dp)
                )
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)

            // Month Selector Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Ноябрь 2026 г.",
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontSize = 15.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(20.dp)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Days of week header (П В С Ч П С В)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("П", "В", "С", "Ч", "П", "С", "В").forEach { day ->
                    Text(
                        text = day,
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFFD4B26F)
                        ),
                        modifier = Modifier.width(36.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Days Grid
            val firstDayOfWeek = month.atDay(1).dayOfWeek.value // Monday = 1
            val daysInMonth = month.lengthOfMonth()
            val totalCells = (firstDayOfWeek - 1) + daysInMonth
            val rows = (totalCells + 6) / 7

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                for (row in 0 until rows) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (col in 0 until 7) {
                            val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                            if (dayNumber in 1..daysInMonth) {
                                val date = month.atDay(dayNumber)
                                val isSelected = date == selectedDate
                                val isVacation = date in vacationDays

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .then(
                                            when {
                                                isSelected -> Modifier.background(Color(0xFFD4B26F))
                                                isVacation -> {
                                                    if (hasTint) {
                                                        Modifier
                                                            .background(greenBorderColor.copy(alpha = 0.18f))
                                                            .border(borderWidth, greenBorderColor, CircleShape)
                                                    } else {
                                                        Modifier.border(borderWidth, greenBorderColor, CircleShape)
                                                    }
                                                }
                                                else -> Modifier
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dayNumber.toString(),
                                        style = TextStyle(
                                            fontFamily = CormorantGaramond,
                                            fontWeight = if (isSelected || isVacation) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 16.sp,
                                            color = when {
                                                isSelected -> Color(0xFF111315)
                                                isVacation -> if (hasTint) greenBorderColor else Color.White
                                                else -> Color.White
                                            }
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

            // Footer Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cancel",
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = "OK",
                    style = TextStyle(
                        fontFamily = CormorantGaramond,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFFD4B26F)
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// PREVIEWS FOR IDE
// -------------------------------------------------------------

@Preview(showBackground = true, widthDp = 380, name = "Option 1 - Emerald Green Border 1.5dp")
@Composable
fun PreviewMaterialDatePickerOption1() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(Color(0xFF111315)).padding(12.dp)) {
            MaterialDatePickerDialogCustom(
                selectedDate = LocalDate.of(2026, 11, 12),
                vacationDays = setOf(
                    LocalDate.of(2026, 11, 15),
                    LocalDate.of(2026, 11, 16),
                    LocalDate.of(2026, 11, 17),
                    LocalDate.of(2026, 11, 18),
                    LocalDate.of(2026, 11, 19),
                    LocalDate.of(2026, 11, 20)
                ),
                greenBorderColor = Color(0xFF10B981),
                borderWidth = 1.5.dp,
                hasTint = false
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Option 2 - Bright Mint Ring 2dp")
@Composable
fun PreviewMaterialDatePickerOption2() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(Color(0xFF111315)).padding(12.dp)) {
            MaterialDatePickerDialogCustom(
                selectedDate = LocalDate.of(2026, 11, 12),
                vacationDays = setOf(
                    LocalDate.of(2026, 11, 15),
                    LocalDate.of(2026, 11, 16),
                    LocalDate.of(2026, 11, 17),
                    LocalDate.of(2026, 11, 18),
                    LocalDate.of(2026, 11, 19),
                    LocalDate.of(2026, 11, 20)
                ),
                greenBorderColor = Color(0xFF34D399),
                borderWidth = 2.dp,
                hasTint = false
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 380, name = "Option 3 - Green Border + Subtle Green Tint")
@Composable
fun PreviewMaterialDatePickerOption3() {
    RetirementTimerAppTheme {
        Box(modifier = Modifier.background(Color(0xFF111315)).padding(12.dp)) {
            MaterialDatePickerDialogCustom(
                selectedDate = LocalDate.of(2026, 11, 12),
                vacationDays = setOf(
                    LocalDate.of(2026, 11, 15),
                    LocalDate.of(2026, 11, 16),
                    LocalDate.of(2026, 11, 17),
                    LocalDate.of(2026, 11, 18),
                    LocalDate.of(2026, 11, 19),
                    LocalDate.of(2026, 11, 20)
                ),
                greenBorderColor = Color(0xFF10B981),
                borderWidth = 1.5.dp,
                hasTint = true
            )
        }
    }
}
