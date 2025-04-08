package com.example.gymclient.presentation.util

import java.time.LocalDate
import java.time.YearMonth

data class CalendarDay(
    val date: LocalDate?,
    val isCurrentMonth: Boolean
)

fun generateMonthDates(year: Int, month: Int): List<CalendarDay> {
    val yearMonth = YearMonth.of(year, month)
    val firstOfMonth = yearMonth.atDay(1)
    val lastOfMonth = yearMonth.atEndOfMonth()

    val days = mutableListOf<CalendarDay>()

    // Padding before the first day (for correct day-of-week start)
    val firstDayOfWeek = firstOfMonth.dayOfWeek.value % 7 // Make Sunday = 0
    repeat(firstDayOfWeek) {
        days.add(CalendarDay(null, false))
    }

    // Add actual days
    for (day in 1..yearMonth.lengthOfMonth()) {
        days.add(CalendarDay(LocalDate.of(year, month, day), true))
    }

    return days
}
