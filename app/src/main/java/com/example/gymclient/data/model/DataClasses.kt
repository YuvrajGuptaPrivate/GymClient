package com.example.gymclient.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date


data class ClientLoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user")val user: User
)

data class User(
    val id: String,
    val name: String,
    val email: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class UpdateClientReq(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String
)
data class Client(
    @SerializedName("_id") val id: String,  // MongoDB ObjectId
    val adminId: String,
    val name: String,
    val email: String,
    val phone: String,
    val dateOfJoining: String, // Can also be String if API returns ISO format
    val paymentType: PaymentType,
    val paymentStatus: PaymentStatus
)

// Enum for Payment Type
enum class PaymentType {
    Monthly, Quarterly, Yearly
}


data class Message(
    val message: String
)



data class AttendanceRecords(
    val success: Boolean,
    val data: List<AttendanceRecord>?
)


data class AttendanceRecord(
    @SerializedName("_id") val id: String,
    val adminId: String,
    val date: String,
    val clientId: String,
    val status: String,
    @SerializedName("clientname") val clientname : String
)


@Entity(
    tableName = "Attendance"
)
data class LocalAttendanceRecord(
    @PrimaryKey
    val id: String,
    val date: Date,
    val status: String)


@Entity(
    tableName = "Payments"
)
data class Payment(
    @PrimaryKey
    @SerializedName("paymentId") val paymentId: String,
    @SerializedName("clientId") val clientId: String,
    @SerializedName("adminId") val adminId: String,
    @SerializedName("amountPaid") val amountPaid: Double,
    @SerializedName("totalAmount") val totalAmount: Double,
    @SerializedName("dueAmount") val dueAmount: Double,
    @SerializedName("nextDueDate") val nextDueDate: String,
    @SerializedName("paymentMode") val paymentMode: PaymentMode,
    @SerializedName("transactionId") val transactionId: String,
    @SerializedName("status") val status: PaymentStatus,
    @SerializedName("clientname")  val clientname: String,
    @SerializedName("paymentDate") val paymentDate: String
)


// Enum for Payment Mode
enum class PaymentMode {
    Cash, Card, UPI, BankTransfer
}

// Enum for Payment Status
enum class PaymentStatus {
    Completed, Pending, Failed
}

data class PaymentsResponse(
    val success: Boolean,
    val payments : List<Payment>
)


data class LoginClientInput(
    @SerializedName("email")  val email: String,
    @SerializedName("password") val password: String,
)

sealed class AuthState {
    object Loading : AuthState()
    data class Authenticated(
        val data: ClientLoginResponse? = null,
        val message: String? = null
    ) : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}


@Entity(tableName = "workout_plans")
data class WorkoutPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Auto ID for Room
    val planName: String,
    val goal: String
)

@Entity(
    tableName = "days",
    foreignKeys = [ForeignKey(
        entity = WorkoutPlanEntity::class,
        parentColumns = ["id"],
        childColumns = ["planId"],
        onDelete = CASCADE
    )]
)
data class DayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val planId: Int,  // Foreign key linking to WorkoutPlan
    val dayNumber: Int,
    val dayTitle: String,
    val description: String?,
    val restTime: String?
)
@Entity(
    tableName = "exercises",
    foreignKeys = [ForeignKey(
        entity = DayEntity::class,
        parentColumns = ["id"],
        childColumns = ["dayId"],
        onDelete = CASCADE
    )]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayId: Int,  // Foreign key linking to Day
    val name: String,
    val equipment: String?,
    val howToPerform: String?,
    val imageSuggestion: String?,
    val restTime: String?,
    val setsReps: String?,
    val targetMuscles: String?,
    val beginnerModification: String?
)
