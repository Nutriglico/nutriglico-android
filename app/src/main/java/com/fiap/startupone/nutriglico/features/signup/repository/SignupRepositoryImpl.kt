package com.fiap.startupone.nutriglico.features.signup.repository

import com.fiap.startupone.nutriglico.features.signup.domain.usecase.User

class SignupRepositoryImpl : SignupRepository {
    override suspend fun createUser(user: User): Result<Boolean> {
        // Aqui você integraria com sua API ou banco de dados
        return try {
            // Simula uma chamada bem-sucedida
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
