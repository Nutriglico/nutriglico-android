package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository

import com.fiap.startupone.nutriglico.commons.model.User

interface UserSignUpRepository {
    suspend fun createUser(user: User): Result<Boolean>
}
