package com.fiap.startupone.nutriglico.features.glucose.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.glucose.register.domain.usecase.GlucoseMeasurement
import com.fiap.startupone.nutriglico.features.glucose.register.domain.usecase.SaveGlucoseMeasurementUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterGlucoseViewModel(
    private val saveGlucoseMeasurementUseCase: SaveGlucoseMeasurementUseCase
) : ViewModel() {

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> get() = _successMessage

    fun saveMeasurement(date: String, time: String, value: Int, condition: String) {
        viewModelScope.launch {
            val measurement = GlucoseMeasurement(date, time, value, condition)
            val isSuccess = saveGlucoseMeasurementUseCase.execute(measurement)
            if (isSuccess) {
                _successMessage.value = "Medição salva com sucesso!"
            } else {
                _successMessage.value = "Falha ao salvar a medição."
            }
        }
    }
}