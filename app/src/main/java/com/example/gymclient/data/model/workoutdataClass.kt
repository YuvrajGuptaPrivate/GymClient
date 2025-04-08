package com.example.gymclient.data.model


import com.google.gson.annotations.SerializedName

data class WorkoutData(
    @SerializedName("workoutPlans")
    val workoutPlans: List<WorkoutPlan>
)

data class WorkoutPlan(
    @SerializedName("days")
    val days: List<Day>,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("planName")
    val planName: String
)


data class Exercise(
    @SerializedName("beginnerModification")
    val beginnerModification: String,
    @SerializedName("equipment")
    val equipment: String,
    @SerializedName("howToPerform")
    val howToPerform: String,
    @SerializedName("imageSuggestion")
    val imageSuggestion: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("restTime")
    val restTime: String,
    @SerializedName("setsReps")
    val setsReps: String,
    @SerializedName("targetMuscles")
    val targetMuscles: String
)


data class Day(
    @SerializedName("dayNumber")
    val dayNumber: Int,
    @SerializedName("dayTitle")
    val dayTitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("exercises")
    val exercises: List<Exercise>,
    @SerializedName("restTime")
    val restTime: String
)