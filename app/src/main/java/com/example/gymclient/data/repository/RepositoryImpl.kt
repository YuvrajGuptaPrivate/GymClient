package com.example.gymclient.data.repository

import android.content.Context
import android.util.Log
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
import com.example.gymclient.domain.Repository
import com.example.gymclient.presentation.util.convertToLocalAttendanceRecords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): Repository {

    override suspend fun getAllClientsAttendance(clientId: String) {
        // Loading local data first
        val localData = localDataSource.getAllAttendance().first() // First() to get initial value


        // Checking if API call is needed
        val shouldFetchFromApi = localData.isEmpty() || isDataStale(localData)

        if (shouldFetchFromApi) {
            try {
                Log.d("FetchAttendance", "Calling API for client: $clientId")
                val remoteAttendances = remoteDataSource.getAllClientsAttendance(clientId).first()

                when (remoteAttendances) {
                    is ApiResponse.Success -> {
                        remoteAttendances.data?.let { attendanceList ->
                            val attendancedata = convertToLocalAttendanceRecords(attendanceList)
                            Log.d("FetchAttendance", "$attendancedata")
                            localDataSource.insertAttendanceListToDb(attendancedata)
                            Log.d("FetchAttendance", "Data fetched successfully from API.")
                        } ?: Log.d("FetchAttendance", "API call successful, but no data returned.")
                    }
                    is ApiResponse.Error -> {
                        Log.e("FetchAttendance", "Error fetching data: ${remoteAttendances.message}")
                    }
                    else -> {
                        Log.e("FetchAttendance", "Unknown error occurred.")
                    }
                }
            } catch (e: Exception) {
                Log.e("FetchAttendance", "API Call Exception: ${e.localizedMessage}", e)
            }
        } else {
            Log.e("FetchAttendance", "No data available to fetch.")
        }
    }






    override suspend fun getAllPaymentRecord(clientId: String): Flow<ApiResponse<PaymentsResponse>> = flow {

        emit(ApiResponse.Loading)

        //  Load local data first
        val localPayments = localDataSource.getAllPayments().first()

        if (localPayments.isNotEmpty()) {
            emit(ApiResponse.Success(PaymentsResponse(success = true, payments = localPayments)))
        }

        //  Check if data is stale or empty
        val shouldFetchFromApi = localPayments.isEmpty()

        if (shouldFetchFromApi) {
            try {
                remoteDataSource.getAllPaymentRecord(clientId)
                    .collect { response ->
                        if (response is ApiResponse.Success) {
                            localDataSource.insertPaymentsListToDb(response.data.payments)

                            val updatedLocalPayments = localDataSource.getAllPayments().first()
                            emit(ApiResponse.Success(PaymentsResponse(success = true, payments = updatedLocalPayments)))
                        } else if (response is ApiResponse.Error) {
                            emit(ApiResponse.Error(response.message))
                        }
                    }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    }.catch { e ->
        emit(ApiResponse.Error(e.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)


    override suspend fun loginClient(inputLogReq: LoginRequest): ApiResponse<ClientLoginResponse> {
        return remoteDataSource.loginClient(inputLogReq)
    }

    override suspend fun getClientsProfile(clientId: String): ApiResponse<Client> {
        return remoteDataSource.getClientsProfile(clientId)
    }

    override suspend fun resetPassword(
        email: String,
        newPassword: String
    ): ApiResponse<Message> {
        return remoteDataSource.resetPassword(email, newPassword)
    }

    override suspend fun updateClientInfo(
        clientId: String,
        updateClientReq: UpdateClientReq
    ): ApiResponse<Client> {
        return remoteDataSource.updateClientInfo(clientId, updateClientReq)
    }

    override fun getAllPayments(): Flow<List<Payment>> {
        return localDataSource.getAllPayments()
    }

    override fun getAllAttendance(): Flow<List<LocalAttendanceRecord>> {
        return localDataSource.getAllAttendance()
    }

    override suspend fun getDateWiseRecord(date: Date): LocalAttendanceRecord {
        return localDataSource.getDateWiseRecord(date)
    }


    private fun isDataStale(data: List<LocalAttendanceRecord>): Boolean {
        if (data.isEmpty()) return true

        val lastRecordDate = data.maxByOrNull { it.date }?.date ?: return true

        // Normalize current date to midnight (00:00:00.000)
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        return lastRecordDate.before(today) // true if data is from a previous day
    }



}
