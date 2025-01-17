package com.fiap.startupone.nutriglico.features.usermanagement.signup.repository

import com.fiap.startupone.nutriglico.commons.model.User
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserResponse
import retrofit2.Response

interface UserManagementRepository {
    suspend fun registerPatient(userRequest: UserRequest): Result<Unit>
    suspend fun registerNutritionist(userRequest: UserRequest): Result<Unit>
    suspend fun updateUser(userId: String, updatedUserRequest: UserRequest): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
    suspend fun getUserDetails(userId: String): Result<UserResponse>
}