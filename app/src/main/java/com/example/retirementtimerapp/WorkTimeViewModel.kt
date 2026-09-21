package com.example.retirementtimerapp

import androidx.lifecycle.ViewModel
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

class WorkTimeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WorkTimeUiState())
    val uiState: StateFlow<WorkTimeUiState> = _uiState.asStateFlow()

    init {
        startTimer()
    }

    fun toggleMode(isWorkTime: Boolean) {
        _uiState.update { it.copy(isWorkTimeMode = isWorkTime) }
    }

    fun updateRetirementDate(date: LocalDate) {
        _uiState.update { it.copy(retirementDate = date) }
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
        // Считаем до конца дня выхода на пенсию (чтобы день был включен полностью)
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
        val workSecondsPerDay = 9 * 3600L // 9 часов в день

        val today = now.toLocalDate()

        // 1. Считаем полные будущие рабочие дни (завтра до самого дня пенсии включительно)
        var futureWorkDays = 0L
        var cursor = today.plusDays(1)
        while (!cursor.isAfter(retirementDate)) {
            if (isWorkDay(cursor)) futureWorkDays++
            cursor = cursor.plusDays(1)
        }

        // 2. Оставшееся время сегодня (если рабочий день)
        var todaySeconds = 0L
        if (isWorkDay(today)) {
            val nowTime = now.toLocalTime()
            if (nowTime.isBefore(endTime)) {
                val effectiveStart = if (nowTime.isBefore(startTime)) startTime else nowTime
                todaySeconds = Duration.between(effectiveStart, endTime).seconds
            }
        }

        // 3. Время в день выхода на пенсию (считаем до времени начала, т.к. пенсия с утра)
        // В данном случае это 0 секунд, так как работа в этот день не начинается.
        val retirementDaySeconds = 0L

        val totalWorkSeconds = (futureWorkDays * workSecondsPerDay) + todaySeconds + retirementDaySeconds

        // Делим общие секунды на 9-часовые рабочие дни
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
        // Стандартная рабочая неделя: Пн-Пт
        return date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
    }
}
