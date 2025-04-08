package com.example.gymclient.domain

import com.example.gymclient.data.model.ApiResponse

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
import javax.inject.Inject

class Usecase @Inject constructor(
    private val repository: Repository) {

    suspend fun getAllClientsAttendance(clientId: String){
         repository.getAllClientsAttendance(clientId)
    }

    suspend fun getAllPaymentRecord(clientId : String) : Flow<ApiResponse<PaymentsResponse>>{
        return repository.getAllPaymentRecord(clientId)
    }

    suspend fun loginClient(inputLogReq : LoginRequest) : ApiResponse<ClientLoginResponse>{
        return repository.loginClient(inputLogReq)
    }

    suspend fun getClientsProfile(clientId : String): ApiResponse<Client>{
        return repository.getClientsProfile(clientId)
    }

    suspend fun resetPassword(email : String, newPassword : String): ApiResponse<Message>{
        return repository.resetPassword(email, newPassword)
    }

    suspend fun updateClientInfo(clientId: String , updateClientReq: UpdateClientReq): ApiResponse<Client>{
        return repository.updateClientInfo(clientId, updateClientReq)
    }

    fun getAllPayments(): Flow<List<Payment>>{
        return repository.getAllPayments()
    }

    fun getAllAttendance(): Flow<List<LocalAttendanceRecord>>{
        return repository.getAllAttendance()
    }

    suspend fun getDateWiseRecord(date: Date): LocalAttendanceRecord{
        return repository.getDateWiseRecord(date)
    }



}