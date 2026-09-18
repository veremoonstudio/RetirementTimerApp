package com.example.retirementtimerapp

import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class Holiday(
    val name: String,
    val month: Int,
    val day: Int,
    val isEnabled: Boolean = true
)

@Serializable
data class TimeUnitsVisibility(
    val showYears: Boolean = true,
    val showMonths: Boolean = true,
    val showDays: Boolean = true,
    val showHours: Boolean = true,
    val showMinutes: Boolean = true,
    val showSeconds: Boolean = true
)

@Serializable
data class RetirementSettings(
    val retirementDateEpoch: Long = LocalDate.now().plusYears(5).toEpochDay(),
    val workDays: Set<DayOfWeek> = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    ),
    val startTimeStr: String = "09:00",
    val endTimeStr: String = "18:00",
    val vacationHours: Int = 0,
    val holidays: List<Holiday> = listOf(
        Holiday("New Year's Day", 1, 1),
        Holiday("Martin Luther King Jr. Day", 1, 16),
        Holiday("Presidents' Day", 2, 20),
        Holiday("Memorial Day", 5, 29)
    ),
    val timeUnits: TimeUnitsVisibility = TimeUnitsVisibility()
) {
    val retirementDate: LocalDate get() = LocalDate.ofEpochDay(retirementDateEpoch)
    val startTime: LocalTime get() = LocalTime.parse(startTimeStr)
    val endTime: LocalTime get() = LocalTime.parse(endTimeStr)
}
