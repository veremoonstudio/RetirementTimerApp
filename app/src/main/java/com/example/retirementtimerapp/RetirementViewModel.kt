package com.example.retirementtimerapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.*
import java.time.temporal.ChronoUnit

data class TimeLeft(
    val years: Long = 0,
    val months: Long = 0,
    val days: Long = 0,
    val hours: Long = 0,
    val minutes: Long = 0,
    val seconds: Long = 0
)

class RetirementViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsManager = SettingsManager(application)
    
    val settings = settingsManager.settingsFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), RetirementSettings()
    )

    private val _isWorkTimeMode = MutableStateFlow(false)
    val isWorkTimeMode = _isWorkTimeMode.asStateFlow()

    private val _timeLeft = MutableStateFlow(TimeLeft())
    val timeLeft = _timeLeft.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                calculateTime()
                delay(1000)
            }
        }
    }

    fun toggleMode(isWorkTime: Boolean) {
        _isWorkTimeMode.value = isWorkTime
        calculateTime()
    }

    fun updateSettings(newSettings: RetirementSettings) {
        viewModelScope.launch {
            settingsManager.updateSettings(newSettings)
            calculateTime()
        }
    }

    private fun calculateTime() {
        val currentSettings = settings.value
        val now = LocalDateTime.now()
        // Устанавливаем момент выхода на пенсию: дата выхода + время начала рабочего дня (свобода начинается с утра)
        val retirementDateTime = currentSettings.retirementDate.atTime(currentSettings.startTime)

        if (now.isAfter(retirementDateTime)) {
            _timeLeft.value = TimeLeft()
            return
        }

        if (_isWorkTimeMode.value) {
            _timeLeft.value = calculateWorkTimeLeft(now, retirementDateTime, currentSettings)
        } else {
            _timeLeft.value = calculateTotalTimeLeft(now, retirementDateTime)
        }
    }

    private fun calculateTotalTimeLeft(now: LocalDateTime, retirement: LocalDateTime): TimeLeft {
        var diff = ChronoUnit.SECONDS.between(now, retirement)
        
        val totalDays = diff / (24 * 3600)
        diff %= (24 * 3600)
        
        val years = totalDays / 365
        val remainingDaysAfterYears = totalDays % 365
        
        val months = remainingDaysAfterYears / 30
        val days = remainingDaysAfterYears % 30
        
        val hours = diff / 3600
        diff %= 3600
        val minutes = diff / 60
        val seconds = diff % 60
        
        return TimeLeft(years, months, days, hours, minutes, seconds)
    }

    private fun calculateWorkTimeLeft(
        now: LocalDateTime,
        retirement: LocalDateTime,
        settings: RetirementSettings
    ): TimeLeft {
        val startTime = settings.startTime
        val endTime = settings.endTime
        val workSecondsPerDay = Duration.between(startTime, endTime).seconds
        if (workSecondsPerDay <= 0) return TimeLeft()

        val today = now.toLocalDate()
        val retirementDate = retirement.toLocalDate()

        // 1. Считаем полные БУДУЩИЕ рабочие дни (между завтра и днем перед пенсией)
        var futureWorkDaysCount = 0L
        var dateCursor = today.plusDays(1)
        while (dateCursor.isBefore(retirementDate)) {
            if (isWorkDay(dateCursor, settings)) {
                futureWorkDaysCount++
            }
            dateCursor = dateCursor.plusDays(1)
        }

        // 2. Считаем остаток рабочих секунд за СЕГОДНЯ
        var todaySeconds = 0L
        if (isWorkDay(today, settings)) {
            val nowTime = now.toLocalTime()
            // Если сейчас еще не вечер и работа не закончилась
            if (nowTime.isBefore(endTime)) {
                val effectiveStart = if (nowTime.isBefore(startTime)) startTime else nowTime
                todaySeconds = Duration.between(effectiveStart, endTime).seconds
            }
        }
        
        // 3. Считаем возможные рабочие секунды в САМ ДЕНЬ выхода на пенсию (до момента выхода)
        var retirementDaySeconds = 0L
        if (retirementDate.isAfter(today) && isWorkDay(retirementDate, settings)) {
            val retirementTime = retirement.toLocalTime()
            if (retirementTime.isAfter(startTime)) {
                val effectiveEnd = if (retirementTime.isAfter(endTime)) endTime else retirementTime
                retirementDaySeconds = Duration.between(startTime, effectiveEnd).seconds
            }
        }

        // Собираем всё в общую копилку секунд
        var totalWorkSeconds = (futureWorkDaysCount * workSecondsPerDay) + todaySeconds + retirementDaySeconds
        
        // Вычитаем отпуск
        totalWorkSeconds -= (settings.vacationHours * 3600L)
        if (totalWorkSeconds < 0) totalWorkSeconds = 0

        // Разбиваем на ячейки
        val days = totalWorkSeconds / workSecondsPerDay
        var remainder = totalWorkSeconds % workSecondsPerDay
        
        val hours = remainder / 3600
        remainder %= 3600
        val minutes = remainder / 60
        val seconds = remainder % 60

        return TimeLeft(0, 0, days, hours, minutes, seconds)
    }

    private fun isWorkDay(date: LocalDate, settings: RetirementSettings): Boolean {
        if (date.dayOfWeek !in settings.workDays) return false
        if (settings.holidays.any { it.isEnabled && it.month == date.monthValue && it.day == date.dayOfMonth }) return false
        return true
    }
}
