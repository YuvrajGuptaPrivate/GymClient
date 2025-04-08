package com.example.gymclient.data

import com.example.gymclient.data.model.AttendanceRecords
import com.example.gymclient.data.model.Client
import com.example.gymclient.data.model.ClientLoginResponse
import com.example.gymclient.data.model.LoginRequest
import com.example.gymclient.data.model.Message
import com.example.gymclient.data.model.PaymentsResponse
import com.example.gymclient.data.model.UpdateClientReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {



    @POST("/api/auth/client-login")
    suspend fun loginClient(@Body loginRequest: LoginRequest) : Response<ClientLoginResponse>


    @GET("/client/{clientId}")
    suspend fun getProfileInfo(
        @Path("clientId") clientId : String
    ) : Response<Client>

    @PUT("/client/reset-password/{email}")
    suspend fun resetPassword(
        @Path("email") email : String,
        @Body newPassword : String
    ): Response<Message>

    @GET("/get-all-attendance/{clientId}")
    suspend fun getAllAttendance(
        @Path("clientId") clientId : String
    ): Response<AttendanceRecords>

    @GET("/get-payment/client/{clientId}")
    suspend  fun getPaymentsRecord(
        @Path("clientId") clientId : String
    ): Response<PaymentsResponse>

    @PUT("/update-client/{clientId}")
    suspend  fun updateProfileInfo(
        @Path("clientId") clientId : String,
        @Body updateClient : UpdateClientReq
    ): Response<Client>



}