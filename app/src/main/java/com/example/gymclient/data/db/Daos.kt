package com.example.gymclient.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gymclient.data.model.DayEntity
import com.example.gymclient.data.model.ExerciseEntity
import com.example.gymclient.data.model.LocalAttendanceRecord
import com.example.gymclient.data.model.Payment
import com.example.gymclient.data.model.WorkoutPlanEntity

import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AttendanceDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendanceListToDb(listOfAttendance: List<LocalAttendanceRecord>)



    @Query("SELECT * FROM attendance ORDER BY date ASC")
    fun getAllAttendance(): Flow<List<LocalAttendanceRecord>>

    @Query("SELECT * FROM attendance WHERE date = :date")
   suspend fun getDatewiseRecord(date: Date): LocalAttendanceRecord

}


@Dao
interface PaymentDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentslistToDb(listOfPayments: List<Payment>)


    @Query("SELECT * FROM Payments")
    fun getAllPayments(): Flow<List<Payment>>

}

@Dao
interface WorkoutDao {
    // Insertions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlans(plans: List<WorkoutPlanEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<DayEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    // Queries
    @Query("SELECT * FROM workout_plans")
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlanEntity>>

    @Query("SELECT * FROM days WHERE planId = :planId")
    fun getDaysForPlan(planId: Int): Flow<List<DayEntity>>

    @Query("SELECT * FROM exercises WHERE dayId = :dayId")
    fun getExercisesForDay(dayId: Int): Flow<List<ExerciseEntity>>

    @Query("SELECT COUNT(*) FROM workout_plans")
    suspend fun getWorkoutPlansCount(): Int
}




