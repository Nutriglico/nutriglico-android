package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.SaveGlicemicControlMeasurementUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterGlicemicControlViewModel(
    private val saveMeasurementUseCase: SaveGlicemicControlMeasurementUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> get() = _uiState

    fun saveMeasurement(glucoseLevel: String, date: String, measurementType: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = saveMeasurementUseCase.execute(glucoseLevel, date, measurementType)
            _uiState.value = if (result.isSuccess) {
                UiState.Success
            } else {
                UiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
