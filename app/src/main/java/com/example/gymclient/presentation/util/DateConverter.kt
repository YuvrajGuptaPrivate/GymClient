package com.example.gymclient.presentation.util

import androidx.room.TypeConverter
import com.example.gymclient.data.model.AttendanceRecords
import com.example.gymclient.data.model.LocalAttendanceRecord
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

fun convertToLocalAttendanceRecords(attendanceRecords: AttendanceRecords): List<LocalAttendanceRecord> {
    val localRecords = mutableListOf<LocalAttendanceRecord>()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Adjusted format

    attendanceRecords.data?.forEach { record ->
        val date: Date = dateFormat.parse(record.date) ?: Date() // Fallback to current date if parsing fails
        localRecords.add(LocalAttendanceRecord(
            id = record.id,
            date = date,
            status = record.status
        ))
    }
    return localRecords
}



class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}


fun Date.toLocalDate(): LocalDate {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
