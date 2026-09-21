package com.example.retirementtimerapp

import java.time.LocalDate

data class WorkTimeUiState(
    val isWorkTimeMode: Boolean = true,
    val retirementDate: LocalDate = LocalDate.of(2026, 8, 6),
    val timeLeft: TimeLeftData = TimeLeftData()
)

data class TimeLeftData(
    val days: String = "00",
    val hours: String = "00",
    val minutes: String = "00",
    val seconds: String = "00"
)
