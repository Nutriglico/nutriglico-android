package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase

data class GlicemicControlMeasurement(
    val date: String,
    val time: String,
    val value: Int,
    val condition: String
)

class SaveGlicemicControlMeasurementUseCase {
    fun execute(measurement: GlicemicControlMeasurement): Boolean {
        // Simula salvamento da medição
        // Aqui seria o ponto para persistir em uma API ou banco de dados
        return measurement.value > 0 // Retorna true se o valor for válido
    }
}