package com.example.gymclient.data.repository

import android.content.Context
import android.util.Log
import com.example.gymclient.data.ApiService
import com.example.gymclient.data.db.AttendanceDao
import com.example.gymclient.data.db.PaymentDao
import com.example.gymclient.data.db.WorkoutDao
import com.example.gymclient.data.model.ApiResponse
import com.example.gymclient.data.model.AttendanceRecord
import com.example.gymclient.data.model.AttendanceRecords
import com.example.gymclient.data.model.Client
import com.example.gymclient.data.model.ClientLoginResponse
import com.example.gymclient.data.model.LocalAttendanceRecord
import com.example.gymclient.data.model.LoginRequest
import com.example.gymclient.data.model.Message
import com.example.gymclient.data.model.Payment
import com.example.gymclient.data.model.PaymentsResponse
import com.example.gymclient.data.model.UpdateClientReq
import com.example.gymclient.data.model.WorkoutPlan
import com.example.gymclient.data.model.WorkoutPlanEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Date
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val attendanceDao: AttendanceDao,
    private val paymentDao: PaymentDao,
    private val workoutDao: WorkoutDao
): LocalDataSource {
    override suspend fun insertAttendanceListToDb(listOfAttendance: List<LocalAttendanceRecord>) {
        attendanceDao.insertAttendanceListToDb(listOfAttendance)
    }

    override fun getAllAttendance(): Flow<List<LocalAttendanceRecord>> {
        return attendanceDao.getAllAttendance()
    }

    override suspend fun getDateWiseRecord(date: Date): LocalAttendanceRecord {
        return attendanceDao.getDatewiseRecord(date)
    }


    override suspend fun insertPaymentsListToDb(listOfPayments: List<Payment>) {
        paymentDao.insertPaymentslistToDb(listOfPayments)
    }

    override fun getAllPayments(): Flow<List<Payment>> {
        return paymentDao.getAllPayments()
    }

}


class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): RemoteDataSource {


    override suspend fun getAllClientsAttendance(clientId: String): Flow<ApiResponse<AttendanceRecords>>
    = flow {
        Log.d("FetchAttendance", "Making API call for client: $clientId")
        val response = apiService.getAllAttendance(clientId)
        Log.d("FetchAttendance", "API call completed with code: ${response.code()}")
        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(ApiResponse.Success(data = data))
            } ?: emit(ApiResponse.Error("Response body is null"))
        } else {
            emit(ApiResponse.Error(response.message(), response.code()))
        }
    }.catch { e ->
        Log.e("FetchAttendance", "API Call Exception: ${e.localizedMessage}", e)
        emit(ApiResponse.Error(e.localizedMessage ?: "Unknown Error"))
    }


    override suspend fun getAllPaymentRecord(clientId: String): Flow<ApiResponse<PaymentsResponse>>
    = flow {
            try {
                val response = apiService.getPaymentsRecord(clientId)
                if (response.isSuccessful){
                    response.body()?.let {
                            body ->
                        emit(ApiResponse.Success(data = body))
                    } ?: emit(ApiResponse.Error("Empty reponse body"))
                } else {
                    emit(ApiResponse.Error("Failed tp fetch payments : ${response.message()}"))
                }
            } catch (e: Exception){
                emit(ApiResponse.Error("Exeception : ${e.message}"))
            }
        }

    override suspend fun loginClient(inputLogReq: LoginRequest): ApiResponse<ClientLoginResponse> {
        val response = apiService.loginClient(inputLogReq)
        if (response.isSuccessful){
            response.body()?.let {
                return ApiResponse.Success(data = it)
            }
        }
        return ApiResponse.Error("no respose provided")    }

    override suspend fun getClientsProfile(clientId: String): ApiResponse<Client> {
        val response = apiService.getProfileInfo(clientId)
        if (response.isSuccessful){
            response.body()?.let {
                return ApiResponse.Success(data = it)
            }
        }
        return ApiResponse.Error("no response provided")
    }

    override suspend fun resetPassword(
        email: String,
        newPassword: String
    ): ApiResponse<Message> {
        val response = apiService.resetPassword(email, newPassword)
        if (response.isSuccessful){
            response.body()?.let {
                return ApiResponse.Success(data = it)
            }
        }
        return ApiResponse.Error("no response provided")
    }

    override suspend fun updateClientInfo(
        clientId: String,
        updateClientReq: UpdateClientReq
    ): ApiResponse<Client> {
        val response = apiService.updateProfileInfo(clientId, updateClientReq)
        if (response.isSuccessful){
            response.body()?.let {
                return ApiResponse.Success(data = it)
            }
        }
        return ApiResponse.Error("no response provided")
    }


}