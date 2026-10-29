package com.veremoonstudio.retirementtimer

import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

@Serializable
data class SettingsData(
    val isWorkTimeMode: Boolean = true,
    val retirementDateEpoch: Long = 0L,
    val workDays: List<DayOfWeek> = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ),
    val startTimeStr: String = "09:00",
    val endTimeStr: String = "18:00",
    val vacationHours: Int = 0,
    val holidays: List<SavedHoliday> = emptyList(),
    val selectedBackground: String = "None"
)

@Serializable
data class SavedHoliday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean = true
)

data class WorkTimeUiState(
    val isWorkTimeMode: Boolean = true,
    val retirementDate: LocalDate = LocalDate.now(),
    val workDays: Set<DayOfWeek> = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ),
    val startTime: LocalTime = LocalTime.of(9, 0),
    val endTime: LocalTime = LocalTime.of(18, 0),
    val vacationHours: Int = 0,
    val holidays: List<Holiday> = listOf(
        Holiday("New Year's Day", 1, 1),
        Holiday("Martin Luther King Jr. Day", 1, 16),
        Holiday("Presidents' Day", 2, 20),
        Holiday("Memorial Day", 5, 29),
        Holiday("Independence Day", 7, 4),
        Holiday("Labor Day", 9, 4),
        Holiday("Columbus Day", 10, 9),
        Holiday("Veterans Day", 11, 11),
        Holiday("Thanksgiving Day", 11, 23),
        Holiday("Christmas Eve", 12, 24),
        Holiday("Christmas Day", 12, 25),
        Holiday("New Year's Eve", 12, 31)
    ),
    val timeLeft: TimeLeftData = TimeLeftData(),
    val selectedBackground: String = "None"
)

data class Holiday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean = true
)

data class TimeLeftData(
    val days: String = "00",
    val hours: String = "00",
    val minutes: String = "00",
    val seconds: String = "00"
)

fun calculateWidgetTimeLeft(settings: SettingsData): TimeLeftData {
    val now = LocalDateTime.now()
    val retirementDate = if (settings.retirementDateEpoch > 0) LocalDate.ofEpochDay(settings.retirementDateEpoch) else LocalDate.now().plusYears(5)
    val workDays = settings.workDays.toSet()
    val startTime = try { LocalTime.parse(settings.startTimeStr) } catch (e: Exception) { LocalTime.of(9, 0) }
    val endTime = try { LocalTime.parse(settings.endTimeStr) } catch (e: Exception) { LocalTime.of(18, 0) }

    if (!settings.isWorkTimeMode) {
        val target = retirementDate.atTime(LocalTime.MAX)
        val duration = if (now.isBefore(target)) Duration.between(now, target) else Duration.ZERO
        return TimeLeftData(
            days = duration.toDays().toString(),
            hours = String.format(Locale.US, "%02d", duration.toHours() % 24),
            minutes = String.format(Locale.US, "%02d", duration.toMinutes() % 60),
            seconds = String.format(Locale.US, "%02d", duration.seconds % 60)
        )
    } else {
        val workSecondsPerDay = Duration.between(startTime, endTime).seconds
        if (workSecondsPerDay <= 0) return TimeLeftData("0", "00", "00", "00")

        val today = now.toLocalDate()
        var futureWorkDays = 0L
        var cursor = today.plusDays(1)
        while (!cursor.isAfter(retirementDate)) {
            val isWorkDay = cursor.dayOfWeek in workDays &&
                    settings.holidays.none { it.isEnabled && it.month == cursor.monthValue && it.day == cursor.dayOfMonth }
            if (isWorkDay) futureWorkDays++
            cursor = cursor.plusDays(1)
        }

        var todaySeconds = 0L
        val isTodayWorkDay = today.dayOfWeek in workDays &&
                settings.holidays.none { it.isEnabled && it.month == today.monthValue && it.day == today.dayOfMonth }
        if (isTodayWorkDay) {
            val nowTime = now.toLocalTime()
            if (nowTime.isBefore(endTime)) {
                val effectiveStart = if (nowTime.isBefore(startTime)) startTime else nowTime
                todaySeconds = Duration.between(effectiveStart, endTime).seconds
            }
        }

        var totalWorkSeconds = (futureWorkDays * workSecondsPerDay) + todaySeconds
        totalWorkSeconds -= (settings.vacationHours * 3600L)
        if (totalWorkSeconds < 0) totalWorkSeconds = 0

        val days = totalWorkSeconds / workSecondsPerDay
        var remainder = totalWorkSeconds % workSecondsPerDay
        val hours = remainder / 3600
        remainder %= 3600
        val minutes = remainder / 60
        val seconds = remainder % 60

        return TimeLeftData(
            days = days.toString(),
            hours = String.format(Locale.US, "%02d", hours),
            minutes = String.format(Locale.US, "%02d", minutes),
            seconds = String.format(Locale.US, "%02d", seconds)
        )
    }
}
