package com.example.retirementtimerapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "work_time_settings")

@Serializable
private data class SettingsData(
    val retirementDateEpoch: Long,
    val workDays: List<DayOfWeek>,
    val startTimeStr: String,
    val endTimeStr: String,
    val vacationHours: Int,
    val holidays: List<SavedHoliday>,
    val selectedBackground: String = "None"
)

@Serializable
private data class SavedHoliday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean
)

class WorkTimeViewModel(application: Application) : AndroidViewModel(application) {
    private val SETTINGS_KEY = stringPreferencesKey("all_settings")
    
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
                            val data = Json.decodeFromString<SettingsData>(json)
                            _uiState.update {
                                it.copy(
                                    retirementDate = LocalDate.ofEpochDay(data.retirementDateEpoch),
                                    workDays = data.workDays.toSet(),
                                    startTime = LocalTime.parse(data.startTimeStr),
                                    endTime = LocalTime.parse(data.endTimeStr),
                                    vacationHours = data.vacationHours,
                                    holidays = data.holidays.map { h -> Holiday(h.name, h.month, h.day, h.isEnabled) },
                                    selectedBackground = data.selectedBackground
                                )
                            }
                        } catch (e: Exception) {
                            // Keep defaults if error
                        }
                    }
                }
        }
    }

    fun toggleMode(isWorkTime: Boolean) {
        _uiState.update { it.copy(isWorkTimeMode = isWorkTime) }
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
                preferences[SETTINGS_KEY] = Json.encodeToString(data)
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
            _uiState.update { it.copy(timeLeft = calculateWorkTime(now, state.retirementDate, state)) }
        } else {
            _uiState.update { it.copy(timeLeft = calculateTotalTime(now, state.retirementDate)) }
        }
    }

    private fun calculateTotalTime(now: LocalDateTime, retirementDate: LocalDate): TimeLeftData {
        val target = retirementDate.atTime(LocalTime.MAX)
        if (now.isAfter(target)) return TimeLeftData("00", "00", "00", "00")

        val duration = Duration.between(now, target)
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60

        return TimeLeftData(
            days = String.format(Locale.US, "%02d", days),
            hours = String.format(Locale.US, "%02d", hours),
            minutes = String.format(Locale.US, "%02d", minutes),
            seconds = String.format(Locale.US, "%02d", seconds)
        )
    }

    private fun calculateWorkTime(now: LocalDateTime, retirementDate: LocalDate, state: WorkTimeUiState): TimeLeftData {
        val startTime = state.startTime
        val endTime = state.endTime
        val workSecondsPerDay = Duration.between(startTime, endTime).seconds

        val today = now.toLocalDate()

        var futureWorkDays = 0L
        var cursor = today.plusDays(1)
        while (!cursor.isAfter(retirementDate)) {
            if (isWorkDay(cursor, state)) futureWorkDays++
            cursor = cursor.plusDays(1)
        }

        var todaySeconds = 0L
        if (isWorkDay(today, state)) {
            val nowTime = now.toLocalTime()
            if (nowTime.isBefore(endTime)) {
                val effectiveStart = if (nowTime.isBefore(startTime)) startTime else nowTime
                todaySeconds = Duration.between(effectiveStart, endTime).seconds
            }
        }

        var totalWorkSeconds = (futureWorkDays * workSecondsPerDay) + todaySeconds
        totalWorkSeconds -= (state.vacationHours * 3600L)
        if (totalWorkSeconds < 0) totalWorkSeconds = 0

        val days = totalWorkSeconds / workSecondsPerDay
        var remainder = totalWorkSeconds % workSecondsPerDay
        
        val hours = remainder / 3600
        remainder %= 3600
        val minutes = remainder / 60
        val seconds = remainder % 60

        return TimeLeftData(
            days = String.format(Locale.US, "%02d", days),
            hours = String.format(Locale.US, "%02d", hours),
            minutes = String.format(Locale.US, "%02d", minutes),
            seconds = String.format(Locale.US, "%02d", seconds)
        )
    }

    private fun isWorkDay(date: LocalDate, state: WorkTimeUiState): Boolean {
        if (date.dayOfWeek !in state.workDays) return false
        if (state.holidays.any { it.isEnabled && it.month == date.monthValue && it.day == date.dayOfMonth }) return false
        return true
    }
}
