package com.veremoonstudio.retirementtimer

import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

val defaultSavedHolidays = listOf(
    SavedHoliday("New Year's Day", 1, 1, true),
    SavedHoliday("Martin Luther King Jr. Day", 1, 16, false),
    SavedHoliday("Presidents' Day", 2, 20, false),
    SavedHoliday("Memorial Day", 5, 29, false),
    SavedHoliday("Independence Day", 7, 4, false),
    SavedHoliday("Labor Day", 9, 4, false),
    SavedHoliday("Columbus Day", 10, 9, false),
    SavedHoliday("Veterans Day", 11, 11, false),
    SavedHoliday("Thanksgiving Day", 11, 23, false),
    SavedHoliday("Christmas Eve", 12, 24, false),
    SavedHoliday("Christmas Day", 12, 25, false),
    SavedHoliday("New Year's Eve", 12, 31, false)
)

val defaultHolidays = listOf(
    Holiday("New Year's Day", 1, 1, true),
    Holiday("Martin Luther King Jr. Day", 1, 16, false),
    Holiday("Presidents' Day", 2, 20, false),
    Holiday("Memorial Day", 5, 29, false),
    Holiday("Independence Day", 7, 4, false),
    Holiday("Labor Day", 9, 4, false),
    Holiday("Columbus Day", 10, 9, false),
    Holiday("Veterans Day", 11, 11, false),
    Holiday("Thanksgiving Day", 11, 23, false),
    Holiday("Christmas Eve", 12, 24, false),
    Holiday("Christmas Day", 12, 25, false),
    Holiday("New Year's Eve", 12, 31, false)
)

@Serializable
data class SettingsData(
    val isWorkTimeMode: Boolean = true,
    val retirementDateEpoch: Long = -1L,
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
    val holidays: List<SavedHoliday> = defaultSavedHolidays,
    val selectedBackground: String = "None"
)

@Serializable
data class SavedHoliday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean = true
)

data class Holiday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean = true
)

data class TimeLeftData(
    val days: String,
    val hours: String,
    val minutes: String,
    val seconds: String
)

data class WorkTimeUiState(
    val isWorkTimeMode: Boolean = true,
    val retirementDate: LocalDate = LocalDate.now().plusYears(5),
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
    val holidays: List<Holiday> = defaultHolidays,
    val selectedBackground: String = "None",
    val timeLeft: TimeLeftData = TimeLeftData("00", "00", "00", "00")
)
