package com.fiap.startupone.nutriglico.features.usermanagement.signup.data

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementResponse

interface UserManagementRepository {
    suspend fun registerPatient(userManagementRequest: UserManagementRequest): Result<Unit>
    suspend fun registerNutritionist(userManagementRequest: UserManagementRequest): Result<Unit>
    suspend fun updateUser(userId: String, updatedUserManagementRequest: UserManagementRequest): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
    suspend fun getUserDetails(userId: String): Result<UserManagementResponse>
}