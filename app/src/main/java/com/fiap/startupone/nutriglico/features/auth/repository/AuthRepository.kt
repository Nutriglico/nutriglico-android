package com.fiap.startupone.nutriglico.features.auth.repository

import com.fiap.startupone.nutriglico.commons.model.User

interface AuthRepository {

    suspend fun login(email: String, password: String): Result<User>

}

