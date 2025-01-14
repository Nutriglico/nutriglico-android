package com.fiap.startupone.nutriglico.features.usermanagement.auth.repository

import com.fiap.startupone.nutriglico.commons.model.User

interface UserAuthRepository {

    suspend fun authenticate(email: String, password: String): Result<User>

}

