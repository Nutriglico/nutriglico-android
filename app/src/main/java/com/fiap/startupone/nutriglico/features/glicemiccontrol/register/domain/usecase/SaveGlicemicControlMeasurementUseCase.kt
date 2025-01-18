package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.repository.RegisterGlicemicControlRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.factory.RegisterGlicemicLevelRequestFactory

class SaveGlicemicControlMeasurementUseCase(
    private val repository: RegisterGlicemicControlRepository,
    private val requestFactory: RegisterGlicemicLevelRequestFactory
) {
    suspend fun execute(
        glucoseLevel: String,
        date: String,
        measurementType: String
    ): Result<Boolean> {
        val requestResult = requestFactory.create(glucoseLevel, date, measurementType)
        if (requestResult.isFailure) {
            return Result.failure(requestResult.exceptionOrNull() ?: Exception("Erro desconhecido"))
        }

        return try {
            val result = repository.registerGlicemicLevel(requestResult.getOrThrow())
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