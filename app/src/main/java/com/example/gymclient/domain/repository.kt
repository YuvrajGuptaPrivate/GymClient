package com.example.gymclient.domain

import android.content.Context
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
import kotlinx.coroutines.flow.Flow
import java.util.Date


interface Repository{


    suspend fun getAllClientsAttendance(clientId: String)

    suspend fun getAllPaymentRecord(clientId : String) : Flow<ApiResponse<PaymentsResponse>>

    suspend fun loginClient(inputLogReq : LoginRequest) : ApiResponse<ClientLoginResponse>

    suspend fun getClientsProfile(clientId : String): ApiResponse<Client>

    suspend fun resetPassword(email : String, newPassword : String): ApiResponse<Message>

    suspend fun updateClientInfo(clientId: String , updateClientReq: UpdateClientReq): ApiResponse<Client>

    fun getAllPayments(): Flow<List<Payment>>

    fun getAllAttendance(): Flow<List<LocalAttendanceRecord>>

    suspend fun getDateWiseRecord(date : Date): LocalAttendanceRecord


}