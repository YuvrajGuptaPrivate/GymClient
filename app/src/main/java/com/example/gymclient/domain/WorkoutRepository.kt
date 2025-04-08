package com.example.gymclient.domain

import android.content.Context
import com.example.gymclient.data.db.WorkoutDao
import com.example.gymclient.data.model.DayEntity
import com.example.gymclient.data.model.ExerciseEntity
import com.example.gymclient.data.model.WorkoutData
import com.example.gymclient.data.model.WorkoutPlanEntity
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.jvm.java


class WorkoutRepository  @Inject constructor(
    private val workoutDao: WorkoutDao,
    @ApplicationContext  val context: Context) {

    suspend fun insertDataFromJson() {
        if (workoutDao.getWorkoutPlansCount() > 0) return

        val json = context.assets.open("workout_plans.json").bufferedReader().use { it.readText() }
        val workoutData = Gson().fromJson(json, WorkoutData::class.java)

        val workoutPlans = mutableListOf<WorkoutPlanEntity>()
        val days = mutableListOf<DayEntity>()
        val exercises = mutableListOf<ExerciseEntity>()

        workoutData.workoutPlans.forEach { plan ->
            val planEntity = WorkoutPlanEntity(planName = plan.planName, goal = plan.goal)
            val planId = workoutPlans.size + 1

            workoutPlans.add(planEntity)

            plan.days.forEach { day ->
                val dayEntity = DayEntity(
                    planId = planId,
                    dayNumber = day.dayNumber,
                    dayTitle = day.dayTitle,
                    description = day.description,
                    restTime = day.restTime
                )
                val dayId = days.size + 1

                days.add(dayEntity)

                day.exercises.forEach { exercise ->
                    exercises.add(
                        ExerciseEntity(
                            dayId = dayId,
                            name = exercise.name,
                            equipment = exercise.equipment,
                            howToPerform = exercise.howToPerform,
                            imageSuggestion = exercise.imageSuggestion,
                            restTime = exercise.restTime,
                            setsReps = exercise.setsReps,
                            targetMuscles = exercise.targetMuscles,
                            beginnerModification = exercise.beginnerModification
                        )
                    )
                }
            }
        }

        // Insert into Room database
        workoutDao.insertWorkoutPlans(workoutPlans)
        workoutDao.insertDays(days)
        workoutDao.insertExercises(exercises)
    }
}
