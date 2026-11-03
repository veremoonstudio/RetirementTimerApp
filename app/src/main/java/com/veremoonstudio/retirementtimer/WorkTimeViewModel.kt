package com.veremoonstudio.retirementtimer

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
                            val targetDate = if (data.retirementDateEpoch > 0L) LocalDate.ofEpochDay(data.retirementDateEpoch) else LocalDate.now().plusYears(5)
                            _uiState.update {
                                it.copy(
                                    isWorkTimeMode = data.isWorkTimeMode,
                                    retirementDate = targetDate,
                                    workDays = data.workDays.toSet(),
                                    startTime = try { LocalTime.parse(data.startTimeStr) } catch (_: Exception) { LocalTime.of(9, 0) },
                                    endTime = try { LocalTime.parse(data.endTimeStr) } catch (_: Exception) { LocalTime.of(18, 0) },
                                    vacationHours = data.vacationHours,
                                    holidays = data.holidays.map { h -> Holiday(h.name, h.month, h.day, h.isEnabled) },
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
            _uiState.update { it.copy(timeLeft = calculateWorkTime(now, state.retirementDate, state)) }
        } else {
            _uiState.update { it.copy(timeLeft = calculateTotalTime(now, state.retirementDate)) }
        }
    }

    private fun calculateTotalTime(now: LocalDateTime, retirementDate: LocalDate): TimeLeftData {
        val target = retirementDate.atTime(LocalTime.MAX)
        if (now.isAfter(target)) {
            val duration = Duration.between(target, now)
            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val minutes = duration.toMinutes() % 60
            val seconds = duration.seconds % 60

            val formattedDays = if (days == 0L) "\u221200" else "\u2212${String.format(Locale.US, "%02d", days)}"
            return TimeLeftData(
                days = formattedDays,
                hours = String.format(Locale.US, "%02d", hours),
                minutes = String.format(Locale.US, "%02d", minutes),
                seconds = String.format(Locale.US, "%02d", seconds)
            )
        }

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
        if (workSecondsPerDay <= 0) return TimeLeftData("00", "00", "00", "00")

        val targetDateTime = retirementDate.atTime(endTime)
        val today = now.toLocalDate()

        if (now.isAfter(targetDateTime)) {
            var pastWorkDays = 0L
            var cursor = retirementDate.plusDays(1)
            val maxCursor = today.minusDays(1)
            var iterations = 0
            while (!cursor.isAfter(maxCursor) && iterations < 3650) {
                if (isWorkDay(cursor, state)) pastWorkDays++
                cursor = cursor.plusDays(1)
                iterations++
            }

            var todaySeconds = 0L
            if (isWorkDay(today, state) && !today.isEqual(retirementDate)) {
                val nowTime = now.toLocalTime()
                if (nowTime.isAfter(startTime)) {
                    val effectiveEnd = if (nowTime.isAfter(endTime)) endTime else nowTime
                    todaySeconds = Duration.between(startTime, effectiveEnd).seconds
                }
            }

            var totalPastWorkSeconds = (pastWorkDays * workSecondsPerDay) + todaySeconds
            if (totalPastWorkSeconds < 0) totalPastWorkSeconds = 0

            val daysPast = totalPastWorkSeconds / workSecondsPerDay
            var remainder = totalPastWorkSeconds % workSecondsPerDay

            val hoursPast = remainder / 3600
            remainder %= 3600
            val minutesPast = remainder / 60
            val secondsPast = remainder % 60

            val formattedDays = if (daysPast == 0L) "\u221200" else "\u2212${String.format(Locale.US, "%02d", daysPast)}"

            return TimeLeftData(
                days = formattedDays,
                hours = String.format(Locale.US, "%02d", hoursPast),
                minutes = String.format(Locale.US, "%02d", minutesPast),
                seconds = String.format(Locale.US, "%02d", secondsPast)
            )
        }

        var futureWorkDays = 0L
        var cursor = today.plusDays(1)
        var iterations = 0
        while (!cursor.isAfter(retirementDate) && iterations < 36500) {
            if (isWorkDay(cursor, state)) futureWorkDays++
            cursor = cursor.plusDays(1)
            iterations++
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
