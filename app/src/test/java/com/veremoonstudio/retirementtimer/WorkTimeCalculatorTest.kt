package com.veremoonstudio.retirementtimer

import org.junit.Assert.*
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class WorkTimeCalculatorTest {

    private val defaultWorkDays = setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    )

    @Test
    fun testIsWorkDay_standardWeekdaysAndWeekends() {
        val holidays = emptyList<Holiday>()

        // 2026-03-02 is a Monday
        val monday = LocalDate.of(2026, 3, 2)
        assertTrue("Monday should be a work day", WorkTimeCalculator.isWorkDay(monday, defaultWorkDays, holidays))

        // 2026-03-07 is a Saturday
        val saturday = LocalDate.of(2026, 3, 7)
        assertFalse("Saturday should not be a work day", WorkTimeCalculator.isWorkDay(saturday, defaultWorkDays, holidays))

        // 2026-03-08 is a Sunday
        val sunday = LocalDate.of(2026, 3, 8)
        assertFalse("Sunday should not be a work day", WorkTimeCalculator.isWorkDay(sunday, defaultWorkDays, holidays))
    }

    @Test
    fun testIsWorkDay_enabledAndDisabledHolidays() {
        val holidays = listOf(
            Holiday("New Year's Day", 1, 1, isEnabled = true),
            Holiday("MLK Day", 1, 19, isEnabled = false)
        )

        // 2026-01-01 is Thursday (New Year's Day, enabled)
        val newYearsDay = LocalDate.of(2026, 1, 1)
        assertFalse("Enabled holiday on a Thursday should not be a work day", WorkTimeCalculator.isWorkDay(newYearsDay, defaultWorkDays, holidays))

        // 2026-01-19 is Monday (MLK Day, disabled)
        val mlkDay = LocalDate.of(2026, 1, 19)
        assertTrue("Disabled holiday on a Monday should remain a work day", WorkTimeCalculator.isWorkDay(mlkDay, defaultWorkDays, holidays))
    }

    @Test
    fun testCalculateTotalTime_futureDate() {
        val now = LocalDateTime.of(2026, 3, 1, 10, 0, 0)
        val retirementDate = LocalDate.of(2026, 3, 11) // 10 days later
        val targetTime = LocalTime.of(15, 30, 0) // 5h 30m after 10:00

        val result = WorkTimeCalculator.calculateTotalTime(now, retirementDate, targetTime)

        assertEquals("10", result.days)
        assertEquals("05", result.hours)
        assertEquals("30", result.minutes)
        assertEquals("00", result.seconds)
    }

    @Test
    fun testCalculateTotalTime_pastDate() {
        val now = LocalDateTime.of(2026, 3, 15, 12, 0, 0)
        val retirementDate = LocalDate.of(2026, 3, 10) // 5 days past
        val targetTime = LocalTime.of(12, 0, 0)

        val result = WorkTimeCalculator.calculateTotalTime(now, retirementDate, targetTime)

        assertEquals("05", result.days)
        assertEquals("00", result.hours)
        assertEquals("00", result.minutes)
        assertEquals("00", result.seconds)
    }

    @Test
    fun testCalculateWorkTime_excludesWeekends() {
        // Monday 2026-03-02 at 09:00:00 (start of workday)
        val now = LocalDateTime.of(2026, 3, 2, 9, 0, 0)
        // Next Monday 2026-03-09 at 18:00:00
        // Full week: Mon Mar 2, Tue Mar 3, Wed Mar 4, Thu Mar 5, Fri Mar 6, Mon Mar 9 = 6 work days (54 working hours)
        // Weekend Mar 7 (Sat) and Mar 8 (Sun) excluded.
        val retirementDate = LocalDate.of(2026, 3, 9)

        val state = WorkTimeUiState(
            isWorkTimeMode = true,
            retirementDate = retirementDate,
            workDays = defaultWorkDays,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(18, 0), // 9h workday
            vacationHours = 0,
            holidays = emptyList()
        )

        val result = WorkTimeCalculator.calculateWorkTime(now, retirementDate, state)

        // Today Mon Mar 2 has 9 hours left (09:00 to 18:00).
        // Future work days: Mar 3, Mar 4, Mar 5, Mar 6, Mar 9 = 5 days.
        // Total work days = 5 days + 9h (1 day) = 6 days 0h 0m 0s
        assertEquals("06", result.days)
        assertEquals("00", result.hours)
        assertEquals("00", result.minutes)
        assertEquals("00", result.seconds)
    }

    @Test
    fun testCalculateWorkTime_holidaySubtractsWorkDay() {
        val now = LocalDateTime.of(2026, 3, 2, 9, 0, 0)
        val retirementDate = LocalDate.of(2026, 3, 9)

        // Add a holiday on Wednesday March 4
        val holidays = listOf(Holiday("Company Day Off", 3, 4, isEnabled = true))

        val state = WorkTimeUiState(
            isWorkTimeMode = true,
            retirementDate = retirementDate,
            workDays = defaultWorkDays,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(18, 0),
            vacationHours = 0,
            holidays = holidays
        )

        val result = WorkTimeCalculator.calculateWorkTime(now, retirementDate, state)

        // Without holiday it was 6 work days. With March 4 excluded, it should be 5 work days.
        assertEquals("05", result.days)
        assertEquals("00", result.hours)
        assertEquals("00", result.minutes)
        assertEquals("00", result.seconds)
    }

    @Test
    fun testCalculateWorkTime_vacationHoursSubtraction() {
        val now = LocalDateTime.of(2026, 3, 2, 9, 0, 0)
        val retirementDate = LocalDate.of(2026, 3, 9) // 6 work days = 54 hours

        // Add 18 vacation hours (2 work days of 9 hours each)
        val state = WorkTimeUiState(
            isWorkTimeMode = true,
            retirementDate = retirementDate,
            workDays = defaultWorkDays,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(18, 0),
            vacationHours = 18,
            holidays = emptyList()
        )

        val result = WorkTimeCalculator.calculateWorkTime(now, retirementDate, state)

        // 6 days - 2 days (18h) = 4 days 0h 0m 0s
        assertEquals("04", result.days)
        assertEquals("00", result.hours)
        assertEquals("00", result.minutes)
        assertEquals("00", result.seconds)
    }

    @Test
    fun testCalculateWorkTime_pastRetirementDate() {
        // Retirement date was 2026-03-02 at 18:00
        val retirementDate = LocalDate.of(2026, 3, 2)
        // Now is Wednesday 2026-03-04 at 18:00 (2 work days past: Tue Mar 3, Wed Mar 4)
        val now = LocalDateTime.of(2026, 3, 4, 18, 0, 0)

        val state = WorkTimeUiState(
            isWorkTimeMode = true,
            retirementDate = retirementDate,
            workDays = defaultWorkDays,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(18, 0),
            vacationHours = 0,
            holidays = emptyList()
        )

        val result = WorkTimeCalculator.calculateWorkTime(now, retirementDate, state)

        // Past work days elapsed since retirement date: Mar 3 (9h) + Mar 4 (9h) = 2 work days
        assertEquals("02", result.days)
        assertEquals("00", result.hours)
        assertEquals("00", result.minutes)
        assertEquals("00", result.seconds)
    }
}
