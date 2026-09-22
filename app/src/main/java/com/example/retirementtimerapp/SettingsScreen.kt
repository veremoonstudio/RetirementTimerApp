package com.example.retirementtimerapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .border(1.dp, RetirementColors.Gold.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uiState.holidays.forEach { holiday ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
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
