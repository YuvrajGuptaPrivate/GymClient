package com.example.gymclient.di

import com.example.gymclient.presentation.util.ClientDataStore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val clientDataStore: ClientDataStore
) {
    // Expose  as a Flow
    val clientId = clientDataStore.getClientIdFlow()

    // Function to save  after login
    suspend fun saveClientId(id: String) {
        clientDataStore.saveClientId(id)
    }

    // Optional: clear  on logout
    suspend fun clearClientId() {
        clientDataStore.clearClientId()
    }
}