package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.SignUpUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.SignUpUserResponse

interface UserManagementRepository {
    suspend fun registerPatient(signUpUserRequest: SignUpUserRequest): Result<Unit>
    suspend fun registerNutritionist(signUpUserRequest: SignUpUserRequest): Result<Unit>
    suspend fun updateUser(userId: String, updatedSignUpUserRequest: SignUpUserRequest): Result<Unit>
    suspend fun deleteUser(userId: String): Result<Unit>
    suspend fun getUserDetails(userId: String): Result<SignUpUserResponse>
}