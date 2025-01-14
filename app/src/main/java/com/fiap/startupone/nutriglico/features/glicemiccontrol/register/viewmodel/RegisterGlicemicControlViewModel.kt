package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.GlicemicControlMeasurement
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.SaveGlicemicControlMeasurementUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterGlicemicControlViewModel(
    private val saveGlicemicControlMeasurementUseCase: SaveGlicemicControlMeasurementUseCase
) : ViewModel() {

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> get() = _successMessage

    fun saveMeasurement(date: String, time: String, value: Int, condition: String) {
        viewModelScope.launch {
            val measurement = GlicemicControlMeasurement(date, time, value, condition)
            val isSuccess = saveGlicemicControlMeasurementUseCase.execute(measurement)
            if (isSuccess) {
                _successMessage.value = "Medição salva com sucesso!"
            } else {
                _successMessage.value = "Falha ao salvar a medição."
            }
        }
    }
}