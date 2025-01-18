package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

import com.fiap.startupone.nutriglico.commons.model.User
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserSignUpRepository

class CreateUserUseCase(private val repository: UserSignUpRepository) {
    suspend fun execute(user: User): Result<Boolean> {
        if (user.name.isBlank() || user.email.isBlank() || user.password.isNullOrBlank()) {
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

