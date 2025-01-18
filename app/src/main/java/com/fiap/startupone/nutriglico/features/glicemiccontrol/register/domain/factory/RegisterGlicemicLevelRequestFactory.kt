package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.factory

import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest

class RegisterGlicemicLevelRequestFactory {
    fun create(glucoseLevel: String, date: String, measurementType: String): Result<RegisterGlicemicLevelRequest> {
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

        return Result.success(request)
    }
}