package com.fiap.startupone.nutriglico.features.auth.data

import com.fiap.startupone.nutriglico.commons.model.User
import com.fiap.startupone.nutriglico.features.auth.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        // Simulação de lógica de login (API ou banco de dados)
        return if (email == "test@example.com" && password == "password123") {
            Result.success(User(id = "1", name = "Test User", email = email))
        } else {
            Result.failure(Exception("Credenciais inválidas"))
        }
    }

}