package com.veremoonstudio.retirementtimer

import android.app.Activity
import com.google.android.ump.ConsentInformation
import com.google.android.ump.UserMessagingPlatform

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.veremoonstudio.retirementtimer.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: WorkTimeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val uriHandler = LocalUriHandler.current
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    MarbleBackground(selectedBackground = uiState.selectedBackground) {
        Scaffold(
            containerColor = Color.Transparent,
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
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingsRow(
                        label = stringResource(R.string.end_time),
                        value = uiState.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        onClick = { showEndTimePicker = true }
                    )
                }

                // Vacation Hours
                item {
                    SettingsSectionTitle(stringResource(R.string.vacation_title))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.vacation_hours),
                            style = TextStyle(
                                fontFamily = PlayfairDisplay,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = RetirementColors.Cream
                            )
                        )
                        var isFocused by remember { mutableStateOf(false) }
                        var textValue by remember { mutableStateOf(uiState.vacationHours.toString()) }

                        LaunchedEffect(uiState.vacationHours) {
                            if (!isFocused) {
                                textValue = uiState.vacationHours.toString()
                            }
                        }

                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(38.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.White.copy(alpha = 0.03f))
                                .border(1.dp, RetirementColors.Gold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = textValue,
                                onValueChange = { newValue ->
                                    val cleanInput = newValue.filter { char -> char.isDigit() }
                                    textValue = cleanInput
                                    val hours = cleanInput.toIntOrNull() ?: 0
                                    viewModel.updateVacationHours(hours)
                                },
                                textStyle = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RetirementColors.Gold,
                                    textAlign = TextAlign.Center
                                ),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        if (textValue.isEmpty()) {
                                            textValue = "0"
                                        }
                                        focusManager.clearFocus()
                                    }
                                ),
                                cursorBrush = SolidColor(RetirementColors.Gold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { focusState ->
                                        isFocused = focusState.isFocused
                                        if (!focusState.isFocused) {
                                            if (textValue.isEmpty()) {
                                                textValue = "0"
                                            }
                                        }
                                    }
                            )
                        }
                    }
                }

                // Holidays
                item {
                    SettingsSectionTitle(stringResource(R.string.holidays_title))
                    var showAddHolidayDialog by remember { mutableStateOf(false) }
                    var holidayToEdit by remember { mutableStateOf<Holiday?>(null) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { showAddHolidayDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.1f),
                                contentColor = RetirementColors.Gold
                            ),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, RetirementColors.Gold.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = stringResource(R.string.add_new_holiday),
                                style = TextStyle(
                                    fontFamily = PlayfairDisplay,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RetirementColors.Gold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        uiState.holidays.forEach { holiday ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = {
                                            val newList = uiState.holidays.map {
                                                if (it == holiday) it.copy(isEnabled = !it.isEnabled) else it
                                            }
                                            viewModel.updateHolidays(newList)
                                        },
                                        onLongClick = { holidayToEdit = holiday }
                                    )
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${holiday.name} (${holiday.month}/${holiday.day})",
                                    style = TextStyle(
                                        fontFamily = PlayfairDisplay,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = RetirementColors.Cream
                                    )
                                )
                                Checkbox(
                                    checked = holiday.isEnabled,
                                    onCheckedChange = { checked ->
                                        val newList = uiState.holidays.map {
                                            if (it == holiday) it.copy(isEnabled = checked) else it
                                        }
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

                    if (showAddHolidayDialog) {
                        AddHolidayDialog(
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

                // Terms & Privacy Policy Links
                item {
                    val termsUrl = stringResource(R.string.terms_url)
                    SettingsRow(
                        label = stringResource(R.string.terms_link),
                        value = "➔",
                        onClick = {
                            try {
                                uriHandler.openUri(termsUrl)
                            } catch (_: Throwable) {}
                        }
                    )
                }

                item {
                    val privacyUrl = stringResource(R.string.privacy_policy_url)
                    SettingsRow(
                        label = stringResource(R.string.privacy_policy_link),
                        value = "➔",
                        onClick = {
                            try {
                                uriHandler.openUri(privacyUrl)
                            } catch (_: Throwable) {}
                        }
                    )
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

                item { Spacer(modifier = Modifier.height(110.dp)) }
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
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SettingsRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp),
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
        modifier = Modifier.border(1.dp, RetirementColors.Gold, RoundedCornerShape(28.dp)),
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
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.ok), color = RetirementColors.Gold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel), color = RetirementColors.Cream)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHolidayDialog(
    holiday: Holiday,
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
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.ok), color = RetirementColors.Gold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel), color = RetirementColors.Cream)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
