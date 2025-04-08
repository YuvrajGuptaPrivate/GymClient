package com.example.gymclient.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymclient.data.model.ApiResponse
import com.example.gymclient.data.model.ClientLoginResponse
import com.example.gymclient.data.model.LoginClientInput
import com.example.gymclient.data.model.LoginRequest
import com.example.gymclient.di.SessionManager
import com.example.gymclient.domain.Usecase
import com.example.gymclient.presentation.viewmodels.AuthState.Authenticated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticatioViewModel @Inject constructor(
    private val usecase: Usecase,
    private val sessionManager: SessionManager
) : ViewModel() {


    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    init {
        checkauthStatus()
    }

    fun checkauthStatus(){

        viewModelScope.launch {
            sessionManager.clientId.collect {
                    id ->
                if(id.isNullOrEmpty()) {
                    _authState.value = AuthState.Unauthenticated
                } else {
                    _authState.value = AuthState.Authenticated()
                }
            }
        }
    }

    fun login(email : String,password: String){

        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        val loginput = LoginRequest(email , password)
        _authState.value = AuthState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = usecase.loginClient(loginput)
            when (response) {
                is ApiResponse.Success -> {
                    val id = response.data.user.id
                    sessionManager.saveClientId(id)
                    _authState.value = Authenticated(response.data)
                }
                is ApiResponse.Error -> {
                    _authState.value = AuthState.Error(response.message)
                }

                else -> {
                }
            }
        }

    }



    fun signOut(){

        viewModelScope.launch {
            sessionManager.clearClientId()
        }
        _authState.value = AuthState.Unauthenticated
    }

}


sealed class AuthState {
    object Loading : AuthState()
    data class Authenticated(
        val data: ClientLoginResponse? = null,
        val message: String? = null
    ) : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}


