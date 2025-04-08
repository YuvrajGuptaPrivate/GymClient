package com.example.gymclient.data.model


sealed class ApiResponse<out T> {
    data class Success<T>(val data: T, val message: String? = null) : ApiResponse<T>()
    data class Error(val message: String, val errorCode: Int? = null) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}