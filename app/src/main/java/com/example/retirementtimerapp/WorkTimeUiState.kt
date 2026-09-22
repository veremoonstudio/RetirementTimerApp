package com.example.retirementtimerapp

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

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
        Holiday("Memorial Day", 5, 29)
    ),
    val timeLeft: TimeLeftData = TimeLeftData()
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
