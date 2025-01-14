package com.fiap.startupone.nutriglico.features.signup.domain.usecase

import com.fiap.startupone.nutriglico.features.signup.repository.SignupRepository

data class User(
    val name: String,
    val email: String,
    val password: String
)

class CreateUserUseCase(private val repository: SignupRepository) {
    suspend fun execute(user: User): Result<Boolean> {
        if (user.name.isBlank() || user.email.isBlank() || user.password.isBlank()) {
            return Result.failure(Exception("Todos os campos são obrigatórios"))
        }

        if (!user.email.contains("@")) {
            return Result.failure(Exception("Email inválido"))
        }

        if (user.password.length < 6) {
            return Result.failure(Exception("A senha deve ter no mínimo 6 caracteres"))
        }

        return repository.createUser(user)
    }
}

