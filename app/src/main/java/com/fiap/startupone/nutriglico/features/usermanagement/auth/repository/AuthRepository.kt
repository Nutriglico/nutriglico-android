package com.fiap.startupone.nutriglico.features.usermanagement.auth.repository

import com.fiap.startupone.nutriglico.commons.model.User

interface AuthRepository {

    suspend fun authenticate(email: String, password: String): Result<User>

}

