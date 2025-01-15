package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.repository.RegisterGlicemicControlRepository

class SaveGlicemicControlMeasurementUseCase(
    private val repository: RegisterGlicemicControlRepository
) {
    suspend fun execute(
        glucoseLevel: String,
        date: String,
        measurementType: String
    ): Result<Boolean> {
        if (glucoseLevel.isEmpty() || date.isEmpty() || measurementType.isEmpty()) {
            return Result.failure(Exception("Preencha todos os campos"))
        }

        val level = try {
            glucoseLevel.toInt()
        } catch (e: NumberFormatException) {
            return Result.failure(Exception("Valor da glicemia inválido"))
        }

        val request = RegisterGlicemicLevelRequest(
            type = measurementType,
            level = level,
            registerDate = date
        )

        return try {
            val result = repository.registerGlicemicLevel(request)
            if (result.isSuccess) {
                Result.success(true)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Erro desconhecido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}