package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository

import com.fiap.startupone.nutriglico.commons.model.User
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserSignUpRepository

class UserSignUpRepositoryImpl : UserSignUpRepository {
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
