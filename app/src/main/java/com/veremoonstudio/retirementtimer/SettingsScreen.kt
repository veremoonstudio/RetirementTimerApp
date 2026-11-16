package com.veremoonstudio.retirementtimer

import android.app.Activity
import android.os.SystemClock
import com.google.android.ump.ConsentInformation
import com.google.android.ump.UserMessagingPlatform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.zIndex
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.Velocity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veremoonstudio.retirementtimer.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: WorkTimeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var showVacationDatePicker by remember { mutableStateOf(false) }
    var editingVacationPeriodId by remember { mutableStateOf<String?>(null) }

    val isPremiumUnlocked by viewModel.billingManager.isPremiumUnlocked.collectAsState()
    val isPremium = BuildConfig.IS_PREMIUM || isPremiumUnlocked
    val bottomPadding = if (!isPremium) 100.dp else 24.dp

    MarbleBackground(selectedBackground = uiState.selectedBackground) {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.settings_title),
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                                letterSpacing = 2.sp,
                                color = RetirementColors.Gold
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = RetirementColors.Gold
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(bottom = bottomPadding),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Work Days
                item {
                    SettingsSectionTitle(stringResource(R.string.work_days_title))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DayOfWeek.entries.forEach { day ->
                            val isSelected = uiState.workDays.contains(day)
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = day.name.take(3),
                                    style = TextStyle(
                                        fontFamily = PlayfairDisplay,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        color = if (isSelected) RetirementColors.Gold else RetirementColors.Cream.copy(alpha = 0.5f)
                                    )
                                )
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { checked ->
                                        val newDays = if (checked) uiState.workDays + day else uiState.workDays - day
                                        viewModel.updateWorkDays(newDays)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = RetirementColors.Gold,
                                        uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f),
                                        checkmarkColor = RetirementColors.Background
                                    )
                                )
                            }
                        }
                    }
                }

                // Time Pickers
                item {
                    SettingsSectionTitle(stringResource(R.string.working_hours_title))
                    SettingsRow(
                        label = stringResource(R.string.start_time),
                        value = uiState.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        onClick = { showStartTimePicker = true }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    SettingsRow(
                        label = stringResource(R.string.end_time),
                        value = uiState.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        onClick = { showEndTimePicker = true }
                    )
                }

                // Vacation Days Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.vacation_title),
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                letterSpacing = 2.sp,
                                color = RetirementColors.Gold
                            )
                        )

                        val totalVacDays = uiState.vacationDaysSet.size
                        if (uiState.vacationPeriods.size > 1 && totalVacDays > 0) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = RetirementColors.Gold,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "total: $totalVacDays d",
                                    style = TextStyle(
                                        fontFamily = PlayfairDisplay,
                                        fontSize = 14.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RetirementColors.Gold
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val fmtDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        val fmtMonthStr = DateTimeFormatter.ofPattern("MMM", Locale.US)
                        val sortedVacationPeriods = remember(uiState.vacationPeriods) {
                            uiState.vacationPeriods.sortedBy { it.startDate }
                        }

                        sortedVacationPeriods.forEach { period ->
                            val firstDate = if (period.startDate.isBefore(period.endDate)) period.startDate else period.endDate
                            val lastDate = if (period.startDate.isBefore(period.endDate)) period.endDate else period.startDate

                            var periodWorkDays = 0
                            var cursor = firstDate
                            var count = 0
                            while (!cursor.isAfter(lastDate) && count < 365) {
                                if (WorkTimeCalculator.isWorkDay(cursor, uiState.workDays, uiState.holidays)) {
                                    periodWorkDays++
                                }
                                cursor = cursor.plusDays(1)
                                count++
                            }

                            val startM = firstDate.format(fmtMonthStr).uppercase(Locale.US)
                            val endM = lastDate.format(fmtMonthStr).uppercase(Locale.US)
                            val monthBadgeText = if (startM == endM) startM else "$startM-$endM"

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 2.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.weight(1f, fill = false)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "VACATION",
                                            style = TextStyle(
                                                fontFamily = PlayfairDisplay,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = RetirementColors.Gold,
                                                letterSpacing = 1.sp
                                            )
                                        )

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(5.dp))
                                                .background(Color(0xFF10B981).copy(alpha = 0.22f))
                                                .border(1.dp, Color(0xFF10B981), RoundedCornerShape(5.dp))
                                                .padding(horizontal = 7.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = monthBadgeText,
                                                style = TextStyle(
                                                    fontFamily = FontFamily.SansSerif,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF34D399),
                                                    letterSpacing = 0.5.sp
                                                )
                                            )
                                        }
                                    }

                                    Text(
                                        text = "${firstDate.format(fmtDate)} – ${lastDate.format(fmtDate)}",
                                        style = TextStyle(
                                            fontFamily = CormorantGaramond,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = RetirementColors.Cream
                                        )
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(78.dp)
                                            .height(36.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color.White.copy(alpha = 0.03f))
                                            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                            .clickable {
                                                editingVacationPeriodId = period.id
                                                showVacationDatePicker = true
                                            }
                                            .padding(horizontal = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.CalendarMonth,
                                                contentDescription = "Edit Vacation",
                                                tint = RetirementColors.Gold,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = "$periodWorkDays d",
                                                style = TextStyle(
                                                    fontFamily = PlayfairDisplay,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = RetirementColors.Gold
                                                )
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = { viewModel.deleteVacationPeriod(period.id) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Delete Vacation",
                                            tint = RetirementColors.Cream.copy(alpha = 0.6f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Variant 3 Gold Pill Button for Adding New Vacation Period
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.03f))
                                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                                .clickable {
                                    editingVacationPeriodId = null
                                    showVacationDatePicker = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add Vacation Period",
                                    tint = RetirementColors.Gold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Add Vacation Period",
                                    style = TextStyle(
                                        fontFamily = PlayfairDisplay,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = RetirementColors.Gold
                                    )
                                )
                            }
                        }
                    }
                }

                // Holidays
                item {
                    SettingsSectionTitle(stringResource(R.string.holidays_title))
                    var showAddHolidayDialog by remember { mutableStateOf(false) }
                    var holidayToEdit by remember { mutableStateOf<Holiday?>(null) }

                    Spacer(modifier = Modifier.height(6.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Variant 3 Gold Pill Button for Adding New Holiday
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White.copy(alpha = 0.03f))
                                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                                .clickable { showAddHolidayDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = stringResource(R.string.add_new_holiday),
                                    tint = RetirementColors.Gold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = stringResource(R.string.add_new_holiday),
                                    style = TextStyle(
                                        fontFamily = PlayfairDisplay,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = RetirementColors.Gold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        val blockParentScrollConnection = remember {
                            object : NestedScrollConnection {
                                override fun onPostScroll(
                                    consumed: Offset,
                                    available: Offset,
                                    source: NestedScrollSource
                                ): Offset {
                                    return available
                                }

                                override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                                    return available
                                }
                            }
                        }

                        var activeReorderHoliday by remember { mutableStateOf<Holiday?>(null) }
                        var lastArrowClickTime by remember { mutableLongStateOf(0L) }
                        var localHolidays by remember(uiState.holidays) { mutableStateOf(uiState.holidays) }
                        val lazyListState = rememberLazyListState()
                        val coroutineScope = rememberCoroutineScope()
                        val density = LocalDensity.current
                        val itemHeightPx = with(density) { 44.dp.toPx() }

                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(230.dp)
                                .nestedScroll(blockParentScrollConnection)
                                .clickable(
                                    enabled = activeReorderHoliday != null,
                                    onClick = { activeReorderHoliday = null }
                                ),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            itemsIndexed(
                                items = localHolidays,
                                key = { _, holiday -> "${holiday.name}_${holiday.month}_${holiday.day}" }
                            ) { index, holiday ->
                                val isReorderingThis = activeReorderHoliday == holiday

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isReorderingThis) Color.White.copy(alpha = 0.08f)
                                            else Color.Transparent
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (isReorderingThis) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier.padding(end = 6.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowDropUp,
                                                    contentDescription = "Move Up",
                                                    tint = if (index > 0) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.3f),
                                                    modifier = Modifier
                                                        .size(18.dp)
                                                        .clickable(enabled = index > 0) {
                                                            val now = SystemClock.elapsedRealtime()
                                                            if (now - lastArrowClickTime < 350L) return@clickable
                                                            lastArrowClickTime = now
                                                            if (index > 0) {
                                                                val targetIndex = index - 1
                                                                val mutable = localHolidays.toMutableList()
                                                                val item = mutable.removeAt(index)
                                                                mutable.add(targetIndex, item)
                                                                localHolidays = mutable
                                                                activeReorderHoliday = holiday
                                                                viewModel.updateHolidays(mutable)
                                                                coroutineScope.launch {
                                                                    val firstVisible = lazyListState.firstVisibleItemIndex
                                                                    if (targetIndex <= firstVisible + 1) {
                                                                        lazyListState.animateScrollToItem(targetIndex)
                                                                    }
                                                                }
                                                            }
                                                        }
                                                )
                                                Icon(
                                                    imageVector = Icons.Default.ArrowDropDown,
                                                    contentDescription = "Move Down",
                                                    tint = if (index < localHolidays.lastIndex) RetirementColors.Gold else RetirementColors.Gold.copy(alpha = 0.3f),
                                                    modifier = Modifier
                                                        .size(18.dp)
                                                        .clickable(enabled = index < localHolidays.lastIndex) {
                                                            val now = SystemClock.elapsedRealtime()
                                                            if (now - lastArrowClickTime < 350L) return@clickable
                                                            lastArrowClickTime = now
                                                            if (index < localHolidays.lastIndex) {
                                                                val targetIndex = index + 1
                                                                val mutable = localHolidays.toMutableList()
                                                                val item = mutable.removeAt(index)
                                                                mutable.add(targetIndex, item)
                                                                localHolidays = mutable
                                                                activeReorderHoliday = holiday
                                                                viewModel.updateHolidays(mutable)
                                                                coroutineScope.launch {
                                                                    val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
                                                                    val lastVisibleIndex = visibleItems.lastOrNull()?.index ?: localHolidays.lastIndex
                                                                    if (targetIndex >= lastVisibleIndex - 1) {
                                                                        lazyListState.animateScrollToItem((targetIndex - 2).coerceAtLeast(0))
                                                                    }
                                                                }
                                                            }
                                                        }
                                                )
                                            }
                                        }

                                        Text(
                                            text = "${holiday.name} (${holiday.month}/${holiday.day})",
                                            style = TextStyle(
                                                fontFamily = PlayfairDisplay,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = if (isReorderingThis) RetirementColors.Gold else RetirementColors.Cream
                                            ),
                                            modifier = Modifier
                                                .weight(1f)
                                                .combinedClickable(
                                                    onClick = {
                                                        if (activeReorderHoliday != null) {
                                                            activeReorderHoliday = null
                                                        } else {
                                                            holidayToEdit = holiday
                                                        }
                                                    },
                                                    onLongClick = {
                                                        activeReorderHoliday = holiday
                                                    }
                                                )
                                        )
                                    }

                                    Checkbox(
                                        checked = holiday.isEnabled,
                                        onCheckedChange = { checked ->
                                            activeReorderHoliday = null
                                            val newList = localHolidays.map {
                                                if (it == holiday) it.copy(isEnabled = checked) else it
                                            }
                                            localHolidays = newList
                                            viewModel.updateHolidays(newList)
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = RetirementColors.Gold,
                                            uncheckedColor = RetirementColors.Gold.copy(alpha = 0.5f)
                                        )
                                    )
                                }
                            }
                        }
                    }

                    if (showAddHolidayDialog) {
                        AddHolidayDialog(
                            vacationPeriods = uiState.vacationPeriods,
                            workDays = uiState.workDays,
                            holidays = uiState.holidays,
                            onDismiss = { showAddHolidayDialog = false },
                            onConfirm = { name, month, day ->
                                val newList = uiState.holidays + Holiday(name, month, day)
                                viewModel.updateHolidays(newList)
                                showAddHolidayDialog = false
                            }
                        )
                    }

                    if (holidayToEdit != null) {
                        EditHolidayDialog(
                            holiday = holidayToEdit!!,
                            vacationPeriods = uiState.vacationPeriods,
                            workDays = uiState.workDays,
                            holidays = uiState.holidays,
                            onDismiss = { holidayToEdit = null },
                            onUpdate = { old: Holiday, new: Holiday ->
                                val newList = uiState.holidays.map { if (it == old) new else it }
                                viewModel.updateHolidays(newList)
                                holidayToEdit = null
                            },
                            onDelete = { holiday: Holiday ->
                                val newList = uiState.holidays.filter { it != holiday }
                                viewModel.updateHolidays(newList)
                                holidayToEdit = null
                            }
                        )
                    }
                }

                // UMP Privacy Options (EEA/UK/US consent choices)
                item {
                    val context = LocalContext.current
                    val activity = context as? Activity
                    val consentInformation = remember(context) {
                        try { UserMessagingPlatform.getConsentInformation(context) } catch (_: Throwable) { null }
                    }
                    val isPrivacyOptionsRequired = consentInformation?.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

                    if (isPrivacyOptionsRequired && activity != null) {
                        SettingsRow(
                            label = "Privacy Settings",
                            value = "➔",
                            onClick = {
                                try {
                                    UserMessagingPlatform.showPrivacyOptionsForm(activity) { _ -> }
                                } catch (_: Throwable) {}
                            }
                        )
                    }
                }


            }
        }

        if (showStartTimePicker) {
            TimePickerDialog(
                onDismiss = { showStartTimePicker = false },
                onConfirm = { hour, minute ->
                    viewModel.updateTimes(LocalTime.of(hour, minute), uiState.endTime)
                    showStartTimePicker = false
                },
                initialHour = uiState.startTime.hour,
                initialMinute = uiState.startTime.minute
            )
        }

        if (showEndTimePicker) {
            TimePickerDialog(
                onDismiss = { showEndTimePicker = false },
                onConfirm = { hour, minute ->
                    viewModel.updateTimes(uiState.startTime, LocalTime.of(hour, minute))
                    showEndTimePicker = false
                },
                initialHour = uiState.endTime.hour,
                initialMinute = uiState.endTime.minute
            )
        }

        if (showVacationDatePicker) {
            val editingPeriod = uiState.vacationPeriods.find { it.id == editingVacationPeriodId }
            val otherVacationAllDays = remember(uiState.vacationPeriods, editingVacationPeriodId) {
                val set = mutableSetOf<LocalDate>()
                uiState.vacationPeriods.filter { it.id != editingVacationPeriodId }.forEach { p ->
                    val first = if (p.startDate.isBefore(p.endDate)) p.startDate else p.endDate
                    val last = if (p.startDate.isBefore(p.endDate)) p.endDate else p.startDate
                    var cursor = first
                    var count = 0
                    while (!cursor.isAfter(last) && count < 365) {
                        set.add(cursor)
                        cursor = cursor.plusDays(1)
                        count++
                    }
                }
                set
            }

            CompactVacationDateRangePickerDialog(
                vacationStartDate = editingPeriod?.startDate ?: (if (editingVacationPeriodId == null) null else uiState.vacationStartDate),
                vacationEndDate = editingPeriod?.endDate ?: (if (editingVacationPeriodId == null) null else uiState.vacationEndDate),
                otherVacationAllDays = otherVacationAllDays,
                workDays = uiState.workDays,
                holidays = uiState.holidays,
                onDismissRequest = {
                    showVacationDatePicker = false
                    editingVacationPeriodId = null
                },
                onRangeSelected = { start, end ->
                    if (start != null && end != null) {
                        val targetId = editingVacationPeriodId
                        if (targetId == null) {
                            viewModel.addVacationPeriod(start, end)
                        } else {
                            viewModel.updateVacationPeriod(targetId, start, end)
                        }
                    }
                    showVacationDatePicker = false
                    editingVacationPeriodId = null
                }
            )
        }
    }
}

@Composable
fun CompactVacationDateRangePickerDialog(
    vacationStartDate: LocalDate?,
    vacationEndDate: LocalDate?,
    otherVacationAllDays: Set<LocalDate> = emptySet(),
    workDays: Set<DayOfWeek> = emptySet(),
    holidays: List<Holiday> = emptyList(),
    onDismissRequest: () -> Unit,
    onRangeSelected: (LocalDate?, LocalDate?) -> Unit
) {
    var tempStart by remember { mutableStateOf(vacationStartDate) }
    var tempEnd by remember { mutableStateOf(vacationEndDate) }

    val localeRu = remember { Locale.forLanguageTag("ru") }
    val headlineText = remember(tempStart, tempEnd) {
        when {
            tempStart == null -> "Нажмите дату начала"
            tempEnd == null -> {
                "${tempStart?.format(DateTimeFormatter.ofPattern("d MMM yyyy", localeRu))} - ..."
            }
            else -> {
                val s = tempStart!!
                val e = tempEnd!!
                val first = if (s.isBefore(e)) s else e
                val last = if (s.isBefore(e)) e else s
                if (first.year == last.year) {
                    "${first.format(DateTimeFormatter.ofPattern("d MMM", localeRu))} - ${last.format(DateTimeFormatter.ofPattern("d MMM yyyy 'г.'", localeRu))}"
                } else {
                    "${first.format(DateTimeFormatter.ofPattern("d MMM yyyy", localeRu))} - ${last.format(DateTimeFormatter.ofPattern("d MMM yyyy 'г.'", localeRu))}"
                }
            }
        }
    }

    val fullVacationRangeSet = remember(tempStart, tempEnd) {
        val s = tempStart
        val e = tempEnd
        if (s == null) emptySet()
        else {
            val first = if (e != null && s.isAfter(e)) e else s
            val last = if (e != null && s.isAfter(e)) s else (e ?: s)
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
                .height(550.dp)
                .clip(RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                ) {
                    // Title
                Text(
                    text = "Выберите даты отпуска",
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 14.sp,
                        color = RetirementColors.Gold,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Headline
                Text(
                    text = headlineText,
                    style = TextStyle(
                        fontFamily = PlayfairDisplay,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = RetirementColors.Gold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(8.dp))

                // Days of week header (Mo Tu We Th Fr Sa Su)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { day ->
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

                Spacer(modifier = Modifier.height(6.dp))

                val focusMonth = remember(vacationStartDate) {
                    vacationStartDate?.let { YearMonth.from(it) } ?: YearMonth.now()
                }
                val minYear = 2000
                val maxYear = 2071
                val baseMonth = remember { YearMonth.of(minYear, 1) }
                val totalMonths = (maxYear - minYear + 1) * 12
                val monthList = remember(baseMonth) {
                    (0 until totalMonths).map { baseMonth.plusMonths(it.toLong()) }
                }
                val focusIndex = remember(monthList, focusMonth) {
                    monthList.indexOf(focusMonth).coerceAtLeast(0)
                }
                val listState = rememberLazyListState(initialFirstVisibleItemIndex = focusIndex)

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(monthList) { month ->
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            // Month Header - COMPACT SPACING
                            Text(
                                text = month.format(DateTimeFormatter.ofPattern("LLLL yyyy 'г.'", localeRu)).replaceFirstChar { it.uppercase() },
                                style = TextStyle(
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.9f)
                                ),
                                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                            )

                            // Days Grid
                            val firstDayOfWeek = month.atDay(1).dayOfWeek.value
                            val daysInMonth = month.lengthOfMonth()
                            val totalCells = (firstDayOfWeek - 1) + daysInMonth
                            val rows = (totalCells + 6) / 7

                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                for (row in 0 until rows) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        for (col in 0 until 7) {
                                            val dayNumber = row * 7 + col - (firstDayOfWeek - 2)
                                            if (dayNumber in 1..daysInMonth) {
                                                val date = month.atDay(dayNumber)
                                                val isInVacationRange = date in fullVacationRangeSet || date in otherVacationAllDays
                                                val isWorkDay = WorkTimeCalculator.isWorkDay(date, workDays, holidays)
                                                val isHoliday = Pair(date.monthValue, date.dayOfMonth) in enabledHolidaySet
                                                val isNonWorkDay = date.dayOfWeek !in workDays

                                                Box(
                                                    modifier = Modifier
                                                        .size(36.dp)
                                                        .clip(CircleShape)
                                                        .then(
                                                            when {
                                                                isInVacationRange && isWorkDay -> Modifier
                                                                    .background(Color(0xFF10B981).copy(alpha = 0.06f))
                                                                    .border(1.dp, Color(0xFF10B981), CircleShape)
                                                                isInVacationRange && !isWorkDay -> Modifier
                                                                    .border(1.dp, Color.White.copy(alpha = 0.45f), CircleShape)
                                                                else -> Modifier
                                                            }
                                                        )
                                                        .clickable {
                                                            val s = tempStart
                                                            val e = tempEnd
                                                            if (s == null || e != null) {
                                                                tempStart = date
                                                                tempEnd = null
                                                            } else {
                                                                if (date >= s) {
                                                                    tempEnd = date
                                                                } else {
                                                                    tempStart = date
                                                                    tempEnd = null
                                                                }
                                                            }
                                                        },
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = dayNumber.toString(),
                                                        style = TextStyle(
                                                            fontFamily = FontFamily.SansSerif,
                                                            fontWeight = if (isHoliday) FontWeight.Bold else FontWeight.Normal,
                                                            fontSize = 15.sp,
                                                            textDecoration = TextDecoration.None,
                                                            color = when {
                                                                isHoliday -> RetirementColors.Gold
                                                                isInVacationRange && !isWorkDay -> Color.White.copy(alpha = 0.85f)
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
                    }
                }

                // Footer Buttons (Dedicated bottom panel with contrasting container background)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2B2930))
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            tempStart = null
                            tempEnd = null
                        }
                    ) {
                        Text(
                            text = "Clear",
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = RetirementColors.Gold.copy(alpha = 0.8f)
                            )
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onDismissRequest) {
                            Text(
                                text = stringResource(R.string.cancel),
                                style = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        TextButton(
                            onClick = {
                                val s = tempStart
                                val e = tempEnd
                                if (s != null && e != null && s.isAfter(e)) {
                                    onRangeSelected(e, s)
                                } else {
                                    onRangeSelected(s, e)
                                }
                                onDismissRequest()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.ok),
                                style = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = RetirementColors.Gold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        style = TextStyle(
            fontFamily = PlayfairDisplay,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 2.sp,
            color = RetirementColors.Gold
        ),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
fun SettingsRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = RetirementColors.Cream
            )
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = PlayfairDisplay,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = RetirementColors.Gold
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit,
    initialHour: Int,
    initialMinute: Int
) {
    val state = rememberTimePickerState(initialHour = initialHour, initialMinute = initialMinute)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(state.hour, state.minute) }) {
                Text(stringResource(R.string.ok), color = RetirementColors.Gold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), color = RetirementColors.Cream)
            }
        },
        containerColor = RetirementColors.Background,
        text = {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                TimePicker(
                    state = state,
                    modifier = Modifier.scale(0.85f),
                    colors = TimePickerDefaults.colors(
                        clockDialColor = Color.White.copy(alpha = 0.05f),
                        clockDialSelectedContentColor = RetirementColors.Background,
                        clockDialUnselectedContentColor = RetirementColors.Cream,
                        selectorColor = RetirementColors.Gold,
                        periodSelectorBorderColor = RetirementColors.Gold,
                        periodSelectorSelectedContainerColor = RetirementColors.Gold,
                        periodSelectorUnselectedContainerColor = Color.Transparent,
                        periodSelectorSelectedContentColor = RetirementColors.Background,
                        periodSelectorUnselectedContentColor = RetirementColors.Cream,
                        timeSelectorSelectedContainerColor = RetirementColors.Gold,
                        timeSelectorUnselectedContainerColor = Color.White.copy(alpha = 0.05f),
                        timeSelectorSelectedContentColor = RetirementColors.Background,
                        timeSelectorUnselectedContentColor = RetirementColors.Cream
                    )
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHolidayDialog(
    vacationPeriods: List<VacationPeriod> = emptyList(),
    workDays: Set<DayOfWeek> = emptySet(),
    holidays: List<Holiday> = emptyList(),
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.border(1.dp, RetirementColors.Gold, RoundedCornerShape(28.dp)),
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onConfirm(name, selectedDate.monthValue, selectedDate.dayOfMonth) },
                enabled = name.isNotBlank()
            ) {
                Text(stringResource(R.string.add), color = RetirementColors.Gold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel), color = RetirementColors.Cream)
            }
        },
        containerColor = RetirementColors.Background,
        title = {
            Text(
                stringResource(R.string.add_new_holiday),
                style = TextStyle(fontFamily = PlayfairDisplay, color = RetirementColors.Gold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.holiday_name), color = RetirementColors.Gold.copy(alpha = 0.7f)) },
                    textStyle = TextStyle(color = RetirementColors.Cream),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RetirementColors.Gold,
                        unfocusedBorderColor = RetirementColors.Gold.copy(alpha = 0.5f),
                        cursorColor = RetirementColors.Gold
                    ),
                    singleLine = true
                )

                SettingsRow(
                    label = stringResource(R.string.date_label),
                    value = selectedDate.format(DateTimeFormatter.ofPattern("MMMM d")),
                    onClick = { showDatePicker = true }
                )
            }
        }
    )

    if (showDatePicker) {
        MaterialVacationDatePickerDialog(
            initialDate = selectedDate,
            vacationPeriods = vacationPeriods,
            workDays = workDays,
            holidays = holidays,
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                selectedDate = date
            },
            onConfirmDateOnly = { date ->
                selectedDate = date
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHolidayDialog(
    holiday: Holiday,
    vacationPeriods: List<VacationPeriod> = emptyList(),
    workDays: Set<DayOfWeek> = emptySet(),
    holidays: List<Holiday> = emptyList(),
    onDismiss: () -> Unit,
    onUpdate: (Holiday, Holiday) -> Unit,
    onDelete: (Holiday) -> Unit
) {
    var name by remember { mutableStateOf(holiday.name) }
    var selectedDate by remember {
        mutableStateOf(
            try {
                LocalDate.of(LocalDate.now().year, holiday.month, holiday.day)
            } catch (e: Exception) {
                try {
                    LocalDate.of(2024, holiday.month, holiday.day)
                } catch (_: Exception) {
                    LocalDate.now()
                }
            }
        )
    }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.border(1.dp, RetirementColors.Gold, RoundedCornerShape(28.dp)),
        containerColor = RetirementColors.Background,
        title = {
            Text(
                stringResource(R.string.edit_holiday),
                style = TextStyle(fontFamily = PlayfairDisplay, color = RetirementColors.Gold)
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.holiday_name), color = RetirementColors.Gold.copy(alpha = 0.7f)) },
                    textStyle = TextStyle(color = RetirementColors.Cream, fontSize = 16.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RetirementColors.Gold,
                        unfocusedBorderColor = RetirementColors.Gold.copy(alpha = 0.5f),
                        cursorColor = RetirementColors.Gold
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                SettingsRow(
                    label = stringResource(R.string.date_label),
                    value = selectedDate.format(DateTimeFormatter.ofPattern("MMMM d")),
                    onClick = { showDatePicker = true }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onUpdate(
                        holiday,
                        holiday.copy(
                            name = name,
                            month = selectedDate.monthValue,
                            day = selectedDate.dayOfMonth
                        )
                    )
                }
            ) {
                Text(stringResource(R.string.update), color = RetirementColors.Gold)
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = { onDelete(holiday) }) {
                    Text(stringResource(R.string.delete), color = Color(0xFFCF6679))
                }
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel), color = RetirementColors.Cream)
                }
            }
        }
    )

    if (showDatePicker) {
        MaterialVacationDatePickerDialog(
            initialDate = selectedDate,
            vacationPeriods = vacationPeriods,
            workDays = workDays,
            holidays = holidays,
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                selectedDate = date
            },
            onConfirmDateOnly = { date ->
                selectedDate = date
                showDatePicker = false
            }
        )
    }
}
