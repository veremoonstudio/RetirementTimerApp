package com.veremoonstudio.retirementtimer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veremoonstudio.retirementtimer.ui.theme.PlayfairDisplay
import com.veremoonstudio.retirementtimer.ui.theme.RetirementColors
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialVacationDatePickerDialog(
    initialDate: LocalDate,
    vacationPeriods: List<VacationPeriod>,
    workDays: Set<DayOfWeek>,
    holidays: List<Holiday>,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onConfirmDateOnly: (LocalDate) -> Unit = {}
) {
    var selectedDate by remember { mutableStateOf(initialDate) }
    var isTextInputMode by remember { mutableStateOf(false) }
    var textInputDate by remember(selectedDate) { mutableStateOf(selectedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))) }
    val localeRu = remember { Locale.forLanguageTag("ru") }

    // Precalculate all vacation days (workdays vs non-workdays)
    val vacationDaysMap = remember(vacationPeriods, workDays, holidays) {
        val map = mutableMapOf<LocalDate, Boolean>()
        for (period in vacationPeriods) {
            val first = if (period.startDate.isBefore(period.endDate)) period.startDate else period.endDate
            val last = if (period.startDate.isBefore(period.endDate)) period.endDate else period.startDate
            var cursor = first
            var count = 0
            while (!cursor.isAfter(last) && count < 365) {
                val isWorkDay = WorkTimeCalculator.isWorkDay(cursor, workDays, holidays)
                map[cursor] = isWorkDay
                cursor = cursor.plusDays(1)
                count++
            }
        }
        map
    }

    val enabledHolidaySet = remember(holidays) {
        holidays.filter { it.isEnabled }.map { Pair(it.month, it.day) }.toSet()
    }

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
            Column(modifier = Modifier.fillMaxWidth()) {
            // 1. Top Header Section (Darker Top Area)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.25f))
                    .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_date),
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 14.sp,
                        color = RetirementColors.Gold,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isTextInputMode) "Введенная дата" else selectedDate.format(DateTimeFormatter.ofPattern("d MMM yyyy 'г.'", localeRu)),
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = if (isTextInputMode) 26.sp else 28.sp,
                            fontWeight = FontWeight.Normal,
                            color = RetirementColors.Gold
                        )
                    )

                    IconButton(
                        onClick = { isTextInputMode = !isTextInputMode },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isTextInputMode) Icons.Default.DateRange else Icons.Default.Edit,
                            contentDescription = if (isTextInputMode) "Calendar Mode" else "Text Input Mode",
                            tint = RetirementColors.Gold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.08f), thickness = 1.dp)

            if (isTextInputMode) {
                // 2a. Text Input Mode Layout (Matching Screenshot 2)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RetirementColors.Background)
                        .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = textInputDate,
                        onValueChange = { input ->
                            textInputDate = input
                            val parts = input.trim().split(".", "/", "-")
                            if (parts.size == 3) {
                                val day = parts[0].toIntOrNull()
                                val month = parts[1].toIntOrNull()
                                val year = parts[2].toIntOrNull()
                                if (day != null && month != null && year != null && year in 2000..2071) {
                                    try {
                                        selectedDate = LocalDate.of(year, month, day)
                                    } catch (_: Exception) {}
                                }
                            }
                        },
                        label = { Text("Дата", color = RetirementColors.Gold) },
                        placeholder = { Text("DD.MM.YYYY", color = Color.White.copy(alpha = 0.4f)) },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 18.sp,
                            color = Color.White
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RetirementColors.Gold,
                            unfocusedBorderColor = RetirementColors.Gold.copy(alpha = 0.6f),
                            cursorColor = RetirementColors.Gold,
                            focusedLabelColor = RetirementColors.Gold,
                            unfocusedLabelColor = RetirementColors.Gold.copy(alpha = 0.8f)
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                // 2b. Month Body Section (Graphical Calendar Grid)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RetirementColors.Background)
                        .padding(top = 16.dp, bottom = 12.dp)
                ) {
                val initialMonth = remember(initialDate) { YearMonth.from(initialDate) }
                val minYear = 2000
                val maxYear = 2071
                val baseMonth = remember { YearMonth.of(minYear, 1) }
                val totalMonths = (maxYear - minYear + 1) * 12
                val initialPageIndex = remember(initialMonth, baseMonth) {
                    val diffYears = initialMonth.year - baseMonth.year
                    val diffMonths = initialMonth.monthValue - baseMonth.monthValue
                    (diffYears * 12 + diffMonths).coerceIn(0, totalMonths - 1)
                }

                val pagerState = rememberPagerState(initialPage = initialPageIndex) { totalMonths }
                val coroutineScope = rememberCoroutineScope()
                var isYearPickerVisible by remember { mutableStateOf(false) }

                val currentDisplayedMonth = remember(pagerState.currentPage) {
                    baseMonth.plusMonths(pagerState.currentPage.toLong())
                }

                // Month Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { isYearPickerVisible = !isYearPickerVisible }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentDisplayedMonth.format(DateTimeFormatter.ofPattern("LLLL yyyy 'г.'", localeRu)).replaceFirstChar { it.uppercase() },
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
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(enabled = pagerState.currentPage > 0) {
                                    if (pagerState.currentPage > 0) {
                                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                                    }
                                }
                        )

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next Month",
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(enabled = pagerState.currentPage < totalMonths - 1) {
                                    if (pagerState.currentPage < totalMonths - 1) {
                                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                                    }
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                if (isYearPickerVisible) {
                    // Year Selector Grid
                    val currentYear = currentDisplayedMonth.year
                    val yearsList = (minYear..maxYear).toList()
                    val gridState = rememberLazyGridState(initialFirstVisibleItemIndex = (currentYear - minYear - 1).coerceAtLeast(0))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        state = gridState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(yearsList.size) { index ->
                            val year = yearsList[index]
                            val isSelected = year == currentYear
                            Box(
                                modifier = Modifier
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .background(if (isSelected) RetirementColors.Gold else Color.Transparent)
                                    .border(if (isSelected) 0.dp else 1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(22.dp))
                                    .clickable {
                                        val targetPageIndex = ((year - baseMonth.year) * 12 + (currentDisplayedMonth.monthValue - baseMonth.monthValue)).coerceIn(0, totalMonths - 1)
                                        coroutineScope.launch { pagerState.scrollToPage(targetPageIndex) }
                                        isYearPickerVisible = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = year.toString(),
                                    style = TextStyle(
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 15.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color(0xFF111315) else RetirementColors.Cream
                                    )
                                )
                            }
                        }
                    }
                } else {
                    // Weekday Header (П В С Ч П С В)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
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

                    Spacer(modifier = Modifier.height(10.dp))

                    // Month Pages
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) { pageIndex ->
                        val month = baseMonth.plusMonths(pageIndex.toLong())
                        val firstDayOfWeek = month.atDay(1).dayOfWeek.value
                        val daysInMonth = month.lengthOfMonth()
                        val totalCells = (firstDayOfWeek - 1) + daysInMonth
                        val rows = (totalCells + 6) / 7
                        val today = LocalDate.now()

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            for (row in 0 until rows) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    for (col in 0 until 7) {
                                        val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                                        if (dayNumber in 1..daysInMonth) {
                                            val date = month.atDay(dayNumber)
                                            val isSelectedDate = date == selectedDate
                                            val vacationWorkDayStatus = vacationDaysMap[date]
                                            val isToday = date == today
                                            val isHoliday = Pair(date.monthValue, date.dayOfMonth) in enabledHolidaySet
                                            val isNonWorkDay = date.dayOfWeek !in workDays

                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(CircleShape)
                                                    .then(
                                                        when {
                                                            isSelectedDate -> Modifier.background(RetirementColors.Gold)
                                                            vacationWorkDayStatus == true -> Modifier
                                                                .background(Color(0xFF10B981).copy(alpha = 0.06f))
                                                                .border(1.dp, Color(0xFF10B981), CircleShape)
                                                            vacationWorkDayStatus == false -> Modifier
                                                                .border(1.dp, Color.White.copy(alpha = 0.45f), CircleShape)
                                                            isToday -> Modifier.border(1.dp, RetirementColors.Gold, CircleShape)
                                                            else -> Modifier
                                                        }
                                                    )
                                                    .clickable {
                                                        selectedDate = date
                                                        onDateSelected(date)
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = dayNumber.toString(),
                                                    style = TextStyle(
                                                        fontFamily = FontFamily.SansSerif,
                                                        fontWeight = if (isSelectedDate) FontWeight.Bold else FontWeight.Normal,
                                                        fontSize = 16.sp,
                                                        color = when {
                                                            isSelectedDate -> Color(0xFF111315)
                                                            isHoliday -> RetirementColors.Gold
                                                            vacationWorkDayStatus == false -> Color.White.copy(alpha = 0.85f)
                                                            isNonWorkDay -> Color.White.copy(alpha = 0.45f)
                                                            else -> Color.White
                                                        }
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
                }
            }
            }

            // Footer Action Buttons (Dedicated Panel with contrasting container background)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2B2930))
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(
                    onClick = {
                        if (isTextInputMode) {
                            val parts = textInputDate.trim().split(".", "/", "-")
                            if (parts.size == 3) {
                                val day = parts[0].toIntOrNull()
                                val month = parts[1].toIntOrNull()
                                val year = parts[2].toIntOrNull()
                                if (day != null && month != null && year != null && year in 2000..2071) {
                                    try {
                                        selectedDate = LocalDate.of(year, month, day)
                                    } catch (_: Exception) {}
                                }
                            }
                        }
                        onConfirmDateOnly(selectedDate)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.ok),
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
}
