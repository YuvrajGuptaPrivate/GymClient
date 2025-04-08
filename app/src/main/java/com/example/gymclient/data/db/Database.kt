package com.example.gymclient.data.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gymclient.data.model.DayEntity
import com.example.gymclient.data.model.ExerciseEntity
import com.example.gymclient.data.model.LocalAttendanceRecord
import com.example.gymclient.data.model.Payment
import com.example.gymclient.data.model.WorkoutPlanEntity
import com.example.gymclient.presentation.util.Converters


@Database(entities = [LocalAttendanceRecord::class, Payment::class, WorkoutPlanEntity::class, DayEntity::class, ExerciseEntity::class],
    version = 4,
    exportSchema = false)
@TypeConverters(Converters::class)


abstract class Database : RoomDatabase(){

    abstract fun getAttendance() : AttendanceDao
    abstract fun getPayments() : PaymentDao
    abstract fun getWorkouts() : WorkoutDao


}