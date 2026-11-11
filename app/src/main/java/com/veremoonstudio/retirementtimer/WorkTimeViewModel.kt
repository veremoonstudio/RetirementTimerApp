package com.veremoonstudio.retirementtimer

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

private val jsonParser = Json { ignoreUnknownKeys = true; isLenient = true }

class WorkTimeViewModel(application: Application) : AndroidViewModel(application) {
    private val SETTINGS_KEY = stringPreferencesKey("all_settings")

    val billingManager = BillingManager(application)
    
    private val _uiState = MutableStateFlow(WorkTimeUiState())
    val uiState: StateFlow<WorkTimeUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
        startTimer()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            getApplication<Application>().dataStore.data
                .map { preferences -> preferences[SETTINGS_KEY] }
                .collect { json ->
                    if (json != null) {
                        try {
                            val data = jsonParser.decodeFromString<SettingsData>(json)
                            val targetDate = if (data.retirementDateEpoch != -1L) LocalDate.ofEpochDay(data.retirementDateEpoch) else LocalDate.now().plusYears(5)
                            val holidaysList = if (!json.contains("\"holidays\"") || data.holidays.size <= 2) {
                                defaultHolidays
                            } else {
                                data.holidays.map { h -> Holiday(h.name, h.month, h.day, h.isEnabled) }
                            }
                            _uiState.update {
                                it.copy(
                                    isWorkTimeMode = data.isWorkTimeMode,
                                    retirementDate = targetDate,
                                    workDays = data.workDays.toSet(),
                                    startTime = try { LocalTime.parse(data.startTimeStr) } catch (_: Exception) { LocalTime.of(9, 0) },
                                    endTime = try { LocalTime.parse(data.endTimeStr) } catch (_: Exception) { LocalTime.of(18, 0) },
                                    vacationHours = data.vacationHours,
                                    holidays = holidaysList,
                                    selectedBackground = data.selectedBackground
                                )
                            }
                        } catch (_: Exception) {
                            // Keep defaults if error
                        }
                    }
                }
        }
    }

    fun toggleMode(isWorkTime: Boolean) {
        _uiState.update { it.copy(isWorkTimeMode = isWorkTime) }
        saveAllSettings()
    }

    fun updateRetirementDate(date: LocalDate) {
        _uiState.update { it.copy(retirementDate = date) }
        saveAllSettings()
    }

    fun updateWorkDays(days: Set<DayOfWeek>) {
        _uiState.update { it.copy(workDays = days) }
        saveAllSettings()
    }

    fun updateTimes(start: LocalTime, end: LocalTime) {
        _uiState.update { it.copy(startTime = start, endTime = end) }
        saveAllSettings()
    }

    fun updateVacationHours(hours: Int) {
        _uiState.update { it.copy(vacationHours = hours) }
        saveAllSettings()
    }

    fun updateHolidays(holidays: List<Holiday>) {
        _uiState.update { it.copy(holidays = holidays) }
        saveAllSettings()
    }

    fun updateSelectedBackground(background: String) {
        _uiState.update { it.copy(selectedBackground = background) }
        saveAllSettings()
    }

    private fun saveAllSettings() {
        val state = _uiState.value
        val data = SettingsData(
            isWorkTimeMode = state.isWorkTimeMode,
            retirementDateEpoch = state.retirementDate.toEpochDay(),
            workDays = state.workDays.toList(),
            startTimeStr = state.startTime.toString(),
            endTimeStr = state.endTime.toString(),
            vacationHours = state.vacationHours,
            holidays = state.holidays.map { h -> SavedHoliday(h.name, h.month, h.day, h.isEnabled) },
            selectedBackground = state.selectedBackground
        )
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[SETTINGS_KEY] = jsonParser.encodeToString(data)
            }
            try {
                RetirementGlanceWidget().updateAll(getApplication())
                RetirementWidgetReceiver.scheduleNextMinuteAlarm(getApplication())
            } catch (_: Exception) {
                // Ignore if widget update fails
            }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                updateTimeLeft()
                delay(1000)
            }
        }
    }

    private fun updateTimeLeft() {
        val state = _uiState.value
        val now = LocalDateTime.now()

        if (state.isWorkTimeMode) {
            _uiState.update { it.copy(timeLeft = WorkTimeCalculator.calculateWorkTime(now, state.retirementDate, state)) }
        } else {
            _uiState.update { it.copy(timeLeft = WorkTimeCalculator.calculateTotalTime(now, state.retirementDate, state.endTime)) }
        }
    }
}
