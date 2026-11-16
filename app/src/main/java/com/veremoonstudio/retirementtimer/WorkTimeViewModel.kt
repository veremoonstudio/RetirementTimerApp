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
                .distinctUntilChanged()
                .collect { json ->
                    if (json != null) {
                        try {
                            val data = jsonParser.decodeFromString<SettingsData>(json)
                            val targetDate = if (data.retirementDateEpoch != -1L) LocalDate.ofEpochDay(data.retirementDateEpoch) else LocalDate.now().plusYears(5)
                            val startVac = if (data.vacationStartEpoch != -1L) LocalDate.ofEpochDay(data.vacationStartEpoch) else null
                            val endVac = if (data.vacationEndEpoch != -1L) LocalDate.ofEpochDay(data.vacationEndEpoch) else null

                            val periodsList = data.vacationPeriods.map { p ->
                                VacationPeriod(id = p.id, startDate = LocalDate.ofEpochDay(p.startEpoch), endDate = LocalDate.ofEpochDay(p.endEpoch))
                            }.ifEmpty {
                                if (startVac != null && endVac != null) {
                                    listOf(VacationPeriod(startDate = startVac, endDate = endVac))
                                } else emptyList()
                            }

                            val holidaysList = if (!json.contains("\"holidays\"") || data.holidays.size <= 2) {
                                defaultHolidays
                            } else {
                                data.holidays.map { h -> Holiday(h.name, h.month, h.day, h.isEnabled) }
                            }

                            val sortedPeriodsList = periodsList.sortedBy { it.startDate }
                            val firstP = sortedPeriodsList.firstOrNull()
                            val parsedStart = try { LocalTime.parse(data.startTimeStr) } catch (_: Exception) { LocalTime.of(9, 0) }
                            val rawEnd = try { LocalTime.parse(data.endTimeStr) } catch (_: Exception) { LocalTime.of(18, 0) }
                            val parsedEnd = if (Duration.between(parsedStart, rawEnd).toHours() in 4..16) rawEnd else LocalTime.of(18, 0)

                            val retTime = try {
                                if (json.contains("\"retirementTimeStr\"")) {
                                    LocalTime.parse(data.retirementTimeStr)
                                } else {
                                    if (rawEnd != parsedEnd) rawEnd else LocalTime.of(18, 0)
                                }
                            } catch (_: Exception) { LocalTime.of(18, 0) }

                            val sortMode = try { HolidaySortMode.valueOf(data.holidaySortModeName) } catch (_: Exception) { HolidaySortMode.BY_DATE }

                            _uiState.update {
                                it.copy(
                                    isWorkTimeMode = data.isWorkTimeMode,
                                    retirementDate = targetDate,
                                    retirementTime = retTime,
                                    workDays = data.workDays.toSet(),
                                    startTime = parsedStart,
                                    endTime = parsedEnd,
                                    vacationHours = data.vacationHours,
                                    vacationStartDate = firstP?.startDate ?: startVac,
                                    vacationEndDate = firstP?.endDate ?: endVac,
                                    vacationPeriods = sortedPeriodsList,
                                    holidays = holidaysList,
                                    selectedBackground = data.selectedBackground,
                                    holidaySortMode = sortMode
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

    fun updateRetirementDateAndTime(date: LocalDate, targetTime: LocalTime) {
        _uiState.update {
            it.copy(
                retirementDate = date,
                retirementTime = targetTime
            )
        }
        recalculateVacationHoursAndSave()
    }

    fun updateWorkDays(days: Set<DayOfWeek>) {
        _uiState.update { it.copy(workDays = days) }
        recalculateVacationHoursAndSave()
    }

    fun updateTimes(start: LocalTime, end: LocalTime) {
        _uiState.update { it.copy(startTime = start, endTime = end) }
        recalculateVacationHoursAndSave()
    }

    fun updateVacationHours(hours: Int) {
        _uiState.update { it.copy(vacationHours = hours) }
        saveAllSettings()
    }

    fun addVacationPeriod(start: LocalDate, end: LocalDate) {
        val first = if (start.isBefore(end)) start else end
        val last = if (start.isBefore(end)) end else start
        val newPeriod = VacationPeriod(startDate = first, endDate = last)
        val updatedList = (_uiState.value.vacationPeriods + newPeriod).sortedBy { it.startDate }
        _uiState.update { it.copy(vacationPeriods = updatedList) }
        recalculateVacationHoursAndSave()
    }

    fun updateVacationPeriod(id: String, start: LocalDate, end: LocalDate) {
        val first = if (start.isBefore(end)) start else end
        val last = if (start.isBefore(end)) end else start
        val updatedList = _uiState.value.vacationPeriods.map {
            if (it.id == id) it.copy(startDate = first, endDate = last) else it
        }.sortedBy { it.startDate }
        _uiState.update { it.copy(vacationPeriods = updatedList) }
        recalculateVacationHoursAndSave()
    }

    fun deleteVacationPeriod(id: String) {
        val updatedList = _uiState.value.vacationPeriods.filter { it.id != id }.sortedBy { it.startDate }
        _uiState.update { it.copy(vacationPeriods = updatedList) }
        recalculateVacationHoursAndSave()
    }

    private fun recalculateVacationHoursAndSave() {
        val hoursPerWorkDay = try {
            val startT = _uiState.value.startTime
            val endT = _uiState.value.endTime
            val diff = Duration.between(startT, endT).toHours()
            if (diff > 0) diff.toInt() else 9
        } catch (_: Exception) { 9 }

        val totalVacWorkDays = _uiState.value.vacationDaysSet.size
        val totalHours = totalVacWorkDays * hoursPerWorkDay

        val sortedPeriods = _uiState.value.vacationPeriods.sortedBy { it.startDate }
        val firstPeriod = sortedPeriods.firstOrNull()

        _uiState.update {
            it.copy(
                vacationHours = totalHours,
                vacationPeriods = sortedPeriods,
                vacationStartDate = firstPeriod?.startDate,
                vacationEndDate = firstPeriod?.endDate
            )
        }
        saveAllSettings()
    }

    fun updateHolidays(holidays: List<Holiday>) {
        _uiState.update { it.copy(holidays = holidays) }
        recalculateVacationHoursAndSave()
    }

    fun updateHolidaySortMode(mode: HolidaySortMode) {
        _uiState.update { it.copy(holidaySortMode = mode) }
        saveAllSettings()
    }

    fun updateSelectedBackground(background: String) {
        _uiState.update { it.copy(selectedBackground = background) }
        saveAllSettings()
    }

    private fun saveAllSettings() {
        val state = _uiState.value
        val savedPeriods = state.vacationPeriods.map { p ->
            SavedVacationPeriod(id = p.id, startEpoch = p.startDate.toEpochDay(), endEpoch = p.endDate.toEpochDay())
        }
        val data = SettingsData(
            isWorkTimeMode = state.isWorkTimeMode,
            retirementDateEpoch = state.retirementDate.toEpochDay(),
            workDays = state.workDays.toList(),
            startTimeStr = state.startTime.toString(),
            endTimeStr = state.endTime.toString(),
            retirementTimeStr = state.retirementTime.toString(),
            vacationHours = state.vacationHours,
            vacationStartEpoch = state.vacationStartDate?.toEpochDay() ?: -1L,
            vacationEndEpoch = state.vacationEndDate?.toEpochDay() ?: -1L,
            vacationPeriods = savedPeriods,
            holidays = state.holidays.map { h -> SavedHoliday(h.name, h.month, h.day, h.isEnabled) },
            selectedBackground = state.selectedBackground,
            holidaySortModeName = state.holidaySortMode.name
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
            _uiState.update { it.copy(timeLeft = WorkTimeCalculator.calculateTotalTime(now, state.retirementDate, state.retirementTime)) }
        }
    }
}
