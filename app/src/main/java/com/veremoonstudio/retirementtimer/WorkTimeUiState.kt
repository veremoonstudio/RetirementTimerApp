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
    val holidays: List<Holiday> = emptyList(),
    val selectedBackground: String = "None",
    val timeLeft: TimeLeftData = TimeLeftData("00", "00", "00", "00")
)
