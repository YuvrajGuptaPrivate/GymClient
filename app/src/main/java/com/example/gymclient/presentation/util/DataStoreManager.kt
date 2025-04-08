package com.example.gymclient.presentation.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton

class ClientDataStore @Inject constructor(
    @ApplicationContext private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore("client_prefs")
        private val CLIENT_ID_KEY = stringPreferencesKey("client_id")
    }

    fun getClientIdFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[CLIENT_ID_KEY]
        }
    }

    suspend fun saveClientId(adminId: String) {
        context.dataStore.edit { preferences ->
            preferences[CLIENT_ID_KEY] = adminId
        }
    }

    suspend fun clearClientId() {
        context.dataStore.edit { preferences ->
            preferences.remove(CLIENT_ID_KEY)
        }
    }
}