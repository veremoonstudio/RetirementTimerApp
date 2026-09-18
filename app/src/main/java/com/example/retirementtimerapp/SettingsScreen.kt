package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: RetirementViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsState()
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Work Days", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DayOfWeek.entries.forEach { day ->
                        val isSelected = settings.workDays.contains(day)
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(day.name.take(3), fontSize = 12.sp)
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { checked ->
                                    val newDays = if (checked) {
                                        settings.workDays + day
                                    } else {
                                        settings.workDays - day
                                    }
                                    viewModel.updateSettings(settings.copy(workDays = newDays))
                                }
                            )
                        }
                    }
                }
            }

            item {
                Text("Time Units", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TimeUnitCheckbox("Days", settings.timeUnits.showDays) { 
                        viewModel.updateSettings(settings.copy(timeUnits = settings.timeUnits.copy(showDays = it))) 
                    }
                    TimeUnitCheckbox("Hours", settings.timeUnits.showHours) { 
                        viewModel.updateSettings(settings.copy(timeUnits = settings.timeUnits.copy(showHours = it))) 
                    }
                    TimeUnitCheckbox("Min", settings.timeUnits.showMinutes) { 
                        viewModel.updateSettings(settings.copy(timeUnits = settings.timeUnits.copy(showMinutes = it))) 
                    }
                    TimeUnitCheckbox("Sec", settings.timeUnits.showSeconds) { 
                        viewModel.updateSettings(settings.copy(timeUnits = settings.timeUnits.copy(showSeconds = it))) 
                    }
                }
            }

            item {
                SettingsTimeRow("Start Time", settings.startTimeStr) {
                    showStartTimePicker = true
                }
                SettingsTimeRow("End Time", settings.endTimeStr) {
                    showEndTimePicker = true
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Vacation Hours", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(
                        value = settings.vacationHours.toString(),
                        onValueChange = { 
                            val hours = it.toIntOrNull() ?: 0
                            viewModel.updateSettings(settings.copy(vacationHours = hours))
                        },
                        modifier = Modifier.width(100.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF9C4), shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    Column {
                        Button(
                            onClick = { /* Add New Holiday Logic */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Blue)
                        ) {
                            Text("Add New Holiday")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        settings.holidays.forEach { holiday ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${holiday.name} - ${holiday.month}/${holiday.day}")
                                Checkbox(
                                    checked = holiday.isEnabled,
                                    onCheckedChange = { checked ->
                                        val newHolidays = settings.holidays.map {
                                            if (it == holiday) it.copy(isEnabled = checked) else it
                                        }
                                        viewModel.updateSettings(settings.copy(holidays = newHolidays))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showStartTimePicker) {
        val time = LocalTime.parse(settings.startTimeStr)
        val state = rememberTimePickerState(initialHour = time.hour, initialMinute = time.minute)
        TimePickerDialog(
            onDismiss = { showStartTimePicker = false },
            onConfirm = {
                val newTime = LocalTime.of(state.hour, state.minute)
                viewModel.updateSettings(settings.copy(startTimeStr = newTime.format(DateTimeFormatter.ofPattern("HH:mm"))))
                showStartTimePicker = false
            }
        ) {
            TimePicker(state = state)
        }
    }

    if (showEndTimePicker) {
        val time = LocalTime.parse(settings.endTimeStr)
        val state = rememberTimePickerState(initialHour = time.hour, initialMinute = time.minute)
        TimePickerDialog(
            onDismiss = { showEndTimePicker = false },
            onConfirm = {
                val newTime = LocalTime.of(state.hour, state.minute)
                viewModel.updateSettings(settings.copy(endTimeStr = newTime.format(DateTimeFormatter.ofPattern("HH:mm"))))
                showEndTimePicker = false
            }
        ) {
            TimePicker(state = state)
        }
    }
}

@Composable
fun TimeUnitCheckbox(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp)
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingsTimeRow(label: String, time: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 18.sp)
        Text(text = time, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("OK") }
        },
        text = { content() }
    )
}
