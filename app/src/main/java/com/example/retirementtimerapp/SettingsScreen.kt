package com.example.retirementtimerapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retirementtimerapp.ui.theme.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: WorkTimeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    MarbleBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "SETTINGS",
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
                    SettingsSectionTitle("Work Days")
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
                                        fontFamily = CormorantGaramond,
                                        fontSize = 12.sp,
                                        color = if (isSelected) RetirementColors.Gold else Color.Gray
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
                    SettingsSectionTitle("Working Hours")
                    SettingsRow(
                        label = "Start Time",
                        value = uiState.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        onClick = { showStartTimePicker = true }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SettingsRow(
                        label = "End Time",
                        value = uiState.endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        onClick = { showEndTimePicker = true }
                    )
                }

                // Vacation Hours
                item {
                    SettingsSectionTitle("Vacation")
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
                            text = "Vacation Hours",
                            style = TextStyle(fontFamily = CormorantGaramond, fontSize = 16.sp, color = RetirementColors.Cream)
                        )
                        var textValue by remember(uiState.vacationHours) { mutableStateOf(uiState.vacationHours.toString()) }
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = {
                                textValue = it
                                it.toIntOrNull()?.let { hours -> viewModel.updateVacationHours(hours) }
                            },
                            modifier = Modifier.width(80.dp),
                            textStyle = TextStyle(fontFamily = PlayfairDisplay, color = RetirementColors.Gold, textAlign = TextAlign.End),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RetirementColors.Gold,
                                unfocusedBorderColor = RetirementColors.Gold.copy(alpha = 0.5f),
                                cursorColor = RetirementColors.Gold
                            ),
                            singleLine = true
                        )
                    }
                }

                // Holidays
                item {
                    SettingsSectionTitle("Holidays")
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
                        // Add New Holiday Button
                        Button(
                            onClick = { showAddHolidayDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.1f),
                                contentColor = RetirementColors.Gold
                            ),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, RetirementColors.Gold.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Add New Holiday",
                                style = TextStyle(fontFamily = CormorantGaramond, fontWeight = FontWeight.Bold)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        uiState.holidays.forEach { holiday ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = { /* Checkbox click handles the toggle */ },
                                        onLongClick = { holidayToEdit = holiday }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${holiday.name} (${holiday.month}/${holiday.day})",
                                    style = TextStyle(fontFamily = CormorantGaramond, fontSize = 15.sp, color = RetirementColors.Cream)
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
                
                item { Spacer(modifier = Modifier.height(40.dp)) }
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
        text = title.uppercase(),
        style = TextStyle(
            fontFamily = CormorantGaramond,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp,
            letterSpacing = 2.sp,
            color = RetirementColors.Gold.copy(alpha = 0.8f)
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
            style = TextStyle(fontFamily = CormorantGaramond, fontSize = 16.sp, color = RetirementColors.Cream)
        )
        Text(
            text = value,
            style = TextStyle(fontFamily = PlayfairDisplay, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RetirementColors.Gold)
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
                Text("OK", color = RetirementColors.Gold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = RetirementColors.Cream)
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
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onConfirm(name, selectedDate.monthValue, selectedDate.dayOfMonth) },
                enabled = name.isNotBlank()
            ) {
                Text("ADD", color = RetirementColors.Gold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCEL", color = RetirementColors.Cream)
            }
        },
        containerColor = RetirementColors.Background,
        title = {
            Text(
                "Add New Holiday",
                style = TextStyle(fontFamily = PlayfairDisplay, color = RetirementColors.Gold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Holiday Name", color = RetirementColors.Gold.copy(alpha = 0.7f)) },
                    textStyle = TextStyle(color = RetirementColors.Cream),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RetirementColors.Gold,
                        unfocusedBorderColor = RetirementColors.Gold.copy(alpha = 0.5f),
                        cursorColor = RetirementColors.Gold
                    ),
                    singleLine = true
                )
                
                SettingsRow(
                    label = "Date",
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
                    Text("OK", color = RetirementColors.Gold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("CANCEL", color = RetirementColors.Cream)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun EditHolidayDialog(
    holiday: Holiday,
    onDismiss: () -> Unit,
    onUpdate: (Holiday, Holiday) -> Unit,
    onDelete: (Holiday) -> Unit
) {
    var name by remember { mutableStateOf(holiday.name) }
    var month by remember { mutableStateOf(holiday.month.toString()) }
    var day by remember { mutableStateOf(holiday.day.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF003366), // Deep blue background as seen in the photo
        text = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color.White
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Simple month/day input for matching the photo style
                    OutlinedTextField(
                        value = month,
                        onValueChange = { month = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center),
                        label = { Text("Month", color = Color.White.copy(alpha = 0.6f)) }
                    )
                    OutlinedTextField(
                        value = day,
                        onValueChange = { day = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center),
                        label = { Text("Day", color = Color.White.copy(alpha = 0.6f)) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            val m = month.toIntOrNull() ?: holiday.month
                            val d = day.toIntOrNull() ?: holiday.day
                            onUpdate(holiday, holiday.copy(name = name, month = m, day = d))
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Update", color = Color(0xFF003366), fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { onDelete(holiday) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Delete", color = Color(0xFF003366), fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        confirmButton = {} // Custom buttons inside 'text' to match photo layout
    )
}
