package com.fiap.startupone.nutriglico.features.signup.repository

import com.fiap.startupone.nutriglico.features.signup.domain.usecase.User

interface SignupRepository {
    suspend fun createUser(user: User): Result<Boolean>
}
