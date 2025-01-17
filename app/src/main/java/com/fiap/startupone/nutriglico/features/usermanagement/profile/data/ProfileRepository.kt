package com.fiap.startupone.nutriglico.features.usermanagement.profile.data

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.UserResponse

interface ProfileRepository {
    suspend fun getUserDetails(userId: String): Result<UserResponse>
    suspend fun updateUser(userId: String, userRequest: UserRequest): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
}