package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserManagementRepository
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.SignUpUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.SignUpUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service.UserManagementService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class UserManagementRepositoryImpl(
    private val userManagementService: UserManagementService
) : UserManagementRepository {

    override suspend fun registerPatient(signUpUserRequest: SignUpUserRequest): Result<Unit> = handleApiCall {
        userManagementService.registerPatient(signUpUserRequest)
    }

    override suspend fun registerNutritionist(signUpUserRequest: SignUpUserRequest): Result<Unit> = handleApiCall {
        userManagementService.registerNutritionist(signUpUserRequest)
    }

    override suspend fun updateUser(userId: String, updatedSignUpUserRequest: SignUpUserRequest): Result<Unit> = handleApiCall {
        userManagementService.updateUser(userId, updatedSignUpUserRequest)
    }

    override suspend fun deleteUser(userId: String): Result<Unit> = handleApiCall {
        userManagementService.deleteUser(userId)
    }

    override suspend fun getUserDetails(userId: String): Result<SignUpUserResponse> = handleApiCall {
        userManagementService.getUserDetails(userId)
    }

    private suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(HttpException(response))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}