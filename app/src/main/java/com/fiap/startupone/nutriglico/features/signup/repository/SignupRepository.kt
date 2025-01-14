package com.fiap.startupone.nutriglico.features.signup.repository

import com.fiap.startupone.nutriglico.commons.model.User

interface SignupRepository {
    suspend fun createUser(user: User): Result<Boolean>
}
