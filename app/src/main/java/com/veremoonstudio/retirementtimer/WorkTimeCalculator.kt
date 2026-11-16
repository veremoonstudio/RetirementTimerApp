package com.veremoonstudio.retirementtimer

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

object WorkTimeCalculator {

    fun isWorkDay(
        date: LocalDate,
        workDays: Set<DayOfWeek>,
        holidays: List<Holiday>,
        vacationDays: Set<LocalDate> = emptySet()
    ): Boolean {
        if (date.dayOfWeek !in workDays) return false
        if (date in vacationDays) return false
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
        val workStart = state.startTime
        val rawWorkDiff = Duration.between(workStart, state.endTime).toHours()
        val workEnd = if (rawWorkDiff in 4..16) state.endTime else LocalTime.of(18, 0)
        val rawWorkSeconds = Duration.between(workStart, workEnd).seconds
        val workSecondsPerDay = if (rawWorkSeconds > 0) rawWorkSeconds else 32400L

        val targetTime = state.retirementTime
        val targetDateTime = retirementDate.atTime(targetTime)
        val today = now.toLocalDate()
        val vacationSet = state.vacationDaysSet

        if (now.isAfter(targetDateTime)) {
            var totalPastWorkSeconds = 0L

            if (today == retirementDate) {
                // Target was earlier today
                if (isWorkDay(today, state.workDays, state.holidays, vacationSet)) {
                    val nowTime = now.toLocalTime()
                    if (nowTime > workStart) {
                        val effStart = if (targetTime > workStart) targetTime else workStart
                        val effEnd = if (nowTime < workEnd) nowTime else workEnd
                        if (effEnd > effStart) {
                            totalPastWorkSeconds += Duration.between(effStart, effEnd).seconds
                        }
                    }
                }
            } else {
                // Target date is in the past (retirementDate < today)

                // 1. Elapsed work time on retirementDate itself (after target time up to workEnd)
                if (isWorkDay(retirementDate, state.workDays, state.holidays, vacationSet)) {
                    val effStart = if (targetTime > workStart) targetTime else workStart
                    if (workEnd > effStart) {
                        totalPastWorkSeconds += Duration.between(effStart, workEnd).seconds
                    }
                }

                // 2. Intermediate full work days between retirementDate + 1 and today - 1
                var cursor = retirementDate.plusDays(1)
                val maxCursor = today.minusDays(1)
                var iterations = 0
                while (!cursor.isAfter(maxCursor) && iterations < 36500) {
                    if (isWorkDay(cursor, state.workDays, state.holidays, vacationSet)) {
                        totalPastWorkSeconds += workSecondsPerDay
                    }
                    cursor = cursor.plusDays(1)
                    iterations++
                }

                // 3. Elapsed work time on today (from workStart up to nowTime)
                if (isWorkDay(today, state.workDays, state.holidays, vacationSet)) {
                    val nowTime = now.toLocalTime()
                    if (nowTime > workStart) {
                        val effEnd = if (nowTime < workEnd) nowTime else workEnd
                        if (effEnd > workStart) {
                            totalPastWorkSeconds += Duration.between(workStart, effEnd).seconds
                        }
                    }
                }
            }

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
        while (cursor.isBefore(retirementDate) && iterations < 36500) {
            if (isWorkDay(cursor, state.workDays, state.holidays, vacationSet)) futureWorkDays++
            cursor = cursor.plusDays(1)
            iterations++
        }

        var totalWorkSeconds = 0L

        if (today == retirementDate) {
            // Target is later today
            if (isWorkDay(today, state.workDays, state.holidays, vacationSet)) {
                val nowTime = now.toLocalTime()
                val effStart = if (nowTime > workStart) nowTime else workStart
                val effEnd = if (targetTime < workEnd) targetTime else workEnd
                if (effEnd > effStart) {
                    totalWorkSeconds += Duration.between(effStart, effEnd).seconds
                }
            }
        } else {
            // Future retirement date (> today)

            // 1. Remaining work time today
            if (isWorkDay(today, state.workDays, state.holidays, vacationSet)) {
                val nowTime = now.toLocalTime()
                if (nowTime < workEnd) {
                    val effStart = if (nowTime > workStart) nowTime else workStart
                    totalWorkSeconds += Duration.between(effStart, workEnd).seconds
                }
            }

            // 2. Full work days in between
            totalWorkSeconds += (futureWorkDays * workSecondsPerDay)

            // 3. Work time on retirementDate itself (from workStart up to targetTime)
            if (isWorkDay(retirementDate, state.workDays, state.holidays, vacationSet)) {
                val effEnd = if (targetTime < workEnd) targetTime else workEnd
                if (effEnd > workStart) {
                    totalWorkSeconds += Duration.between(workStart, effEnd).seconds
                }
            }
        }

        if (vacationSet.isEmpty() && state.vacationHours > 0) {
            val vacSeconds = state.vacationHours * 3600L
            if (totalWorkSeconds > vacSeconds) {
                totalWorkSeconds -= vacSeconds
            }
        }
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
