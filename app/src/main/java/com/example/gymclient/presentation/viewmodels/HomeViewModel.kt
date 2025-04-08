package com.example.gymclient.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymclient.data.db.WorkoutDao
import com.example.gymclient.data.model.ApiResponse
import com.example.gymclient.data.model.DayEntity
import com.example.gymclient.data.model.ExerciseEntity
import com.example.gymclient.data.model.LocalAttendanceRecord
import com.example.gymclient.data.model.WorkoutPlanEntity
import com.example.gymclient.di.SessionManager
import com.example.gymclient.domain.Usecase
import com.example.gymclient.domain.WorkoutRepository
import com.example.gymclient.presentation.util.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val Usecase: Usecase,
    private val sessionManager: SessionManager,
    private val workoutRepository: WorkoutRepository,
    private val workoutDao: WorkoutDao
): ViewModel() {



    // Expose the workout plans as a Flow
    val workoutPlans: Flow<List<WorkoutPlanEntity>> = workoutDao.getAllWorkoutPlans()

    // Function to get days for a specific plan
    fun getDaysForPlan(planId: Int): Flow<List<DayEntity>> {
        return workoutDao.getDaysForPlan(planId)
    }

    // Function to get exercises for a specific day
    fun getExercisesForDay(dayId: Int): Flow<List<ExerciseEntity>> {
        return workoutDao.getExercisesForDay(dayId)
    }



    private val _attendanceMap = MutableStateFlow<Map<LocalDate, Boolean>>(emptyMap())
    val attendanceMap: StateFlow<Map<LocalDate, Boolean>> = _attendanceMap

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage : StateFlow<String?> = _errorMessage


    //local
    val localPayments = Usecase.getAllPayments()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    //local
    val localAttendances = Usecase.getAllAttendance()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _recordState = MutableStateFlow<LocalAttendanceRecord?>(null)
    val recordState: StateFlow<LocalAttendanceRecord?> = _recordState





    init {
        loaddata()
        observeClientData()

    }


private fun observeClientData() {
    viewModelScope.launch(Dispatchers.IO) {
        sessionManager.clientId.collect { id ->
            if (!id.isNullOrEmpty()) {
                fetchAttendancefromAPiandSavetoDb(id)
                fetchPaymentFromApiandSaveLocally(id)
            }
        }
    }

    viewModelScope.launch(Dispatchers.IO) {
        localAttendances.collect { list ->
            val map = list.associate {
                val localDate = it.date.toLocalDate()
                val isPresent = it.status.equals("present", ignoreCase = true)
                localDate to isPresent
            }
            _attendanceMap.value = map
        }
    }
}

    fun loaddata(){
        viewModelScope.launch(Dispatchers.IO) {
            workoutRepository.insertDataFromJson()
        }
    }


    fun fetchAttendancefromAPiandSavetoDb(clientId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            Usecase.getAllClientsAttendance(clientId)
            _isLoading.value = false

        }
    }


    fun fetchPaymentFromApiandSaveLocally(clientId: String){
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            Usecase.getAllPaymentRecord(clientId).collect {
                    response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _errorMessage.value = response.message
                        //    Log.d("fetchingpayments error", response.message)
                        _isLoading.value = false
                    }
                    ApiResponse.Loading -> {
                        _isLoading.value = true
                    }
                    is ApiResponse.Success -> {
                        _isLoading.value = false
                        //    Log.d("fetchingpayments", response.data.payments.toString())
                    }
                }
            }

        }

    }


    fun getDatewiseRecord(date : Date){

        if (date.after(Date())) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = Usecase.getDateWiseRecord(date)
            _recordState.value = result
        }
    }






}











