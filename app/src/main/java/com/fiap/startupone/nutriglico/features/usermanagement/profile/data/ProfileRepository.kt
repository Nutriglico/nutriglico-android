package com.fiap.startupone.nutriglico.features.usermanagement.profile.data

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse

interface ProfileRepository {
    suspend fun getUserDetails(userId: String): Result<ProfileUserResponse>
    suspend fun updateUser(userId: String, profileUserRequest: ProfileUserRequest): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
}