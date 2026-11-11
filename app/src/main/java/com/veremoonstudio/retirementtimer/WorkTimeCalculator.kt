package com.veremoonstudio.retirementtimer

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

object WorkTimeCalculator {

    fun isWorkDay(date: LocalDate, workDays: Set<DayOfWeek>, holidays: List<Holiday>): Boolean {
        if (date.dayOfWeek !in workDays) return false
        return holidays.none { it.isEnabled && it.month == date.monthValue && it.day == date.dayOfMonth }
    }

    fun calculateTotalTime(now: LocalDateTime, retirementDate: LocalDate, targetTime: LocalTime): TimeLeftData {
        val target = retirementDate.atTime(targetTime)
        if (now.isAfter(target)) {
            val duration = Duration.between(target, now)
            val days = duration.toDays()
            val hours = duration.toHours() % 24
            val minutes = duration.toMinutes() % 60
            val seconds = duration.seconds % 60

            val formattedDays = String.format(Locale.US, "%02d", days)
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

    fun calculateWorkTime(now: LocalDateTime, retirementDate: LocalDate, state: WorkTimeUiState): TimeLeftData {
        val startTime = state.startTime
        val endTime = state.endTime
        val rawWorkSeconds = Duration.between(startTime, endTime).seconds
        val workSecondsPerDay = if (rawWorkSeconds > 0) rawWorkSeconds else 32400L

        val targetDateTime = retirementDate.atTime(endTime)
        val today = now.toLocalDate()

        if (now.isAfter(targetDateTime)) {
            var pastWorkDays = 0L
            var cursor = retirementDate.plusDays(1)
            val maxCursor = today.minusDays(1)
            var iterations = 0
            while (!cursor.isAfter(maxCursor) && iterations < 36500) {
                if (isWorkDay(cursor, state.workDays, state.holidays)) pastWorkDays++
                cursor = cursor.plusDays(1)
                iterations++
            }

            var todaySeconds = 0L
            if (today.isAfter(retirementDate) && isWorkDay(today, state.workDays, state.holidays)) {
                val nowTime = now.toLocalTime()
                if (nowTime.isAfter(startTime)) {
                    val effectiveEnd = if (nowTime.isAfter(endTime) && endTime > startTime) endTime else nowTime
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

            val formattedDays = String.format(Locale.US, "%02d", daysPast)

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
            if (isWorkDay(cursor, state.workDays, state.holidays)) futureWorkDays++
            cursor = cursor.plusDays(1)
            iterations++
        }

        var todaySeconds = 0L
        if (isWorkDay(today, state.workDays, state.holidays)) {
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
}
