package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.veremoonstudio.retirementtimer.ui.theme.CormorantGaramond
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CustomMaterialDatePickerDialog(
    initialDate: LocalDate = LocalDate.now(),
    selectedRetirementDate: LocalDate? = null,
    vacationDays: Set<LocalDate> = emptySet(),
    workDays: Set<DayOfWeek> = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
    isRangeSelection: Boolean = false,
    vacationStartDate: LocalDate? = null,
    vacationEndDate: LocalDate? = null,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit = {},
    onRangeSelected: (LocalDate?, LocalDate?) -> Unit = { _, _ -> }
) {
    var displayedMonth by remember {
        mutableStateOf(
            YearMonth.from(
                if (isRangeSelection) (vacationStartDate ?: initialDate) else (selectedRetirementDate ?: initialDate)
            )
        )
    }

    var selectedSingleDate by remember { mutableStateOf(selectedRetirementDate ?: initialDate) }
    var tempStart by remember { mutableStateOf(vacationStartDate) }
    var tempEnd by remember { mutableStateOf(vacationEndDate) }

    // Helper to calculate active vacation set in range picker
    val activeVacationSet = remember(tempStart, tempEnd, vacationDays, isRangeSelection) {
        if (!isRangeSelection) {
            vacationDays
        } else {
            val start = tempStart
            if (start == null) {
                emptySet()
            } else {
                val end = tempEnd ?: start
                val first = if (start.isBefore(end)) start else end
                val last = if (start.isBefore(end)) end else start
                val set = mutableSetOf<LocalDate>()
                var cursor = first
                var count = 0
                while (!cursor.isAfter(last) && count < 365) {
                    set.add(cursor)
                    cursor = cursor.plusDays(1)
                    count++
                }
                set
            }
        }
    }

    val greenOutlineColor = Color(0xFF10B981)

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Header Label
                Text(
                    text = if (isRangeSelection) "Выберите дни отпуска" else stringResource(R.string.select_date),
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
                    val localeRu = Locale.forLanguageTag("ru")
                    val headlineText = if (!isRangeSelection) {
                        selectedSingleDate.format(DateTimeFormatter.ofPattern("d MMM yyyy 'г.'", localeRu))
                    } else {
                        when {
                            tempStart == null -> "Нажмите дату начала"
                            tempEnd == null -> "${tempStart?.format(DateTimeFormatter.ofPattern("d MMM", localeRu))} - ..."
                            else -> "${tempStart?.format(DateTimeFormatter.ofPattern("d MMM", localeRu))} - ${tempEnd?.format(DateTimeFormatter.ofPattern("d MMM", localeRu))}"
                        }
                    }

                    Text(
                        text = headlineText,
                        style = TextStyle(
                            fontFamily = CormorantGaramond,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (headlineText.length > 20) 22.sp else 28.sp,
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
                            text = displayedMonth.format(DateTimeFormatter.ofPattern("LLLL yyyy 'г.'", Locale.forLanguageTag("ru"))).replaceFirstChar { it.uppercase() },
                            style = TextStyle(
                                fontFamily = CormorantGaramond,
                                fontSize = 16.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        IconButton(
                            onClick = { displayedMonth = displayedMonth.minusMonths(1) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous Month",
                                tint = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        IconButton(
                            onClick = { displayedMonth = displayedMonth.plusMonths(1) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next Month",
                                tint = Color.White.copy(alpha = 0.9f)
                            )
                        }
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
                val firstDayOfWeek = displayedMonth.atDay(1).dayOfWeek.value // Monday = 1
                val daysInMonth = displayedMonth.lengthOfMonth()
                val totalCells = (firstDayOfWeek - 1) + daysInMonth
                val rows = (totalCells + 6) / 7

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (row in 0 until rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (col in 0 until 7) {
                                val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                                if (dayNumber in 1..daysInMonth) {
                                    val date = displayedMonth.atDay(dayNumber)
                                    val isSelectedRetirement = date == selectedRetirementDate || (!isRangeSelection && date == selectedSingleDate)
                                    val isVacationDay = date in activeVacationSet
                                    val isNonWorkDay = date.dayOfWeek !in workDays

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .then(
                                                when {
                                                    isSelectedRetirement -> Modifier.background(Color(0xFFD4B26F))
                                                    isVacationDay -> Modifier
                                                        .background(greenOutlineColor.copy(alpha = 0.06f))
                                                        .border(1.5.dp, greenOutlineColor, CircleShape)
                                                    else -> Modifier
                                                }
                                            )
                                            .clickable {
                                                if (!isRangeSelection) {
                                                    selectedSingleDate = date
                                                    onDateSelected(date)
                                                    onDismissRequest()
                                                } else {
                                                    val start = tempStart
                                                    val end = tempEnd
                                                    if (start == null || end != null) {
                                                        tempStart = date
                                                        tempEnd = null
                                                    } else {
                                                        if (date >= start) {
                                                            tempEnd = date
                                                        } else {
                                                            tempStart = date
                                                            tempEnd = null
                                                        }
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayNumber.toString(),
                                            style = TextStyle(
                                                fontFamily = CormorantGaramond,
                                                fontWeight = if (isSelectedRetirement) FontWeight.Bold else FontWeight.Normal,
                                                fontSize = 16.sp,
                                                color = when {
                                                    isSelectedRetirement -> Color(0xFF111315)
                                                    isNonWorkDay -> Color.White.copy(alpha = 0.45f)
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
            }

            // Footer Action Buttons (Dedicated bottom panel with contrasting container background)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2B2930))
                    .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isRangeSelection) {
                    TextButton(
                        onClick = {
                            tempStart = null
                            tempEnd = null
                        }
                    ) {
                        Text(
                            text = "Очистить",
                            style = TextStyle(
                                fontFamily = CormorantGaramond,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = RetirementColors.Gold.copy(alpha = 0.8f)
                            )
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = TextStyle(
                                fontFamily = CormorantGaramond,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    TextButton(
                        onClick = {
                            if (!isRangeSelection) {
                                onDateSelected(selectedSingleDate)
                            } else {
                                onRangeSelected(tempStart, tempEnd)
                            }
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.ok),
                            style = TextStyle(
                                fontFamily = CormorantGaramond,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFFD4B26F)
                            )
                        )
                    }
                }
            }
        }
    }
}
