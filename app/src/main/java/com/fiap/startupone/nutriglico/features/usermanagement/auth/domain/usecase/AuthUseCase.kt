package com.fiap.startupone.nutriglico.features.usermanagement.auth.domain.usecase

import com.fiap.startupone.nutriglico.commons.model.User
import com.fiap.startupone.nutriglico.features.usermanagement.auth.repository.AuthRepository

class AuthUseCase(private val repository: AuthRepository) {
    suspend fun execute(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Email e senha são obrigatórios"))
        }
        return repository.authenticate(email, password)
    }
}
