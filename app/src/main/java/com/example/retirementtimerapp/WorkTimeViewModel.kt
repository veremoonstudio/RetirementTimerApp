package com.example.retirementtimerapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "work_time_settings")

class WorkTimeViewModel(application: Application) : AndroidViewModel(application) {
    private val RETIREMENT_DATE_KEY = longPreferencesKey("retirement_date")
    
    private val _uiState = MutableStateFlow(WorkTimeUiState())
    val uiState: StateFlow<WorkTimeUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
        startTimer()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val savedDateEpoch = getApplication<Application>().dataStore.data
                .map { preferences -> preferences[RETIREMENT_DATE_KEY] }
                .firstOrNull()
            
            if (savedDateEpoch != null) {
                _uiState.update { it.copy(retirementDate = LocalDate.ofEpochDay(savedDateEpoch)) }
            } else {
                // If no date saved, use today (already default in WorkTimeUiState)
                _uiState.update { it.copy(retirementDate = LocalDate.now()) }
            }
        }
    }

    fun toggleMode(isWorkTime: Boolean) {
        _uiState.update { it.copy(isWorkTimeMode = isWorkTime) }
    }

    fun updateRetirementDate(date: LocalDate) {
        _uiState.update { it.copy(retirementDate = date) }
        saveSettings(date)
    }

    private fun saveSettings(date: LocalDate) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[RETIREMENT_DATE_KEY] = date.toEpochDay()
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
            _uiState.update { it.copy(timeLeft = calculateWorkTime(now, state.retirementDate)) }
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

    private fun calculateWorkTime(now: LocalDateTime, retirementDate: LocalDate): TimeLeftData {
        val startTime = LocalTime.of(9, 0)
        val endTime = LocalTime.of(18, 0)
        val workSecondsPerDay = 9 * 3600L

        val today = now.toLocalDate()

        var futureWorkDays = 0L
        var cursor = today.plusDays(1)
        while (!cursor.isAfter(retirementDate)) {
            if (isWorkDay(cursor)) futureWorkDays++
            cursor = cursor.plusDays(1)
        }

        var todaySeconds = 0L
        if (isWorkDay(today)) {
            val nowTime = now.toLocalTime()
            if (nowTime.isBefore(endTime)) {
                val effectiveStart = if (nowTime.isBefore(startTime)) startTime else nowTime
                todaySeconds = Duration.between(effectiveStart, endTime).seconds
            }
        }

        val totalWorkSeconds = (futureWorkDays * workSecondsPerDay) + todaySeconds
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

    private fun isWorkDay(date: LocalDate): Boolean {
        return date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
    }
}
