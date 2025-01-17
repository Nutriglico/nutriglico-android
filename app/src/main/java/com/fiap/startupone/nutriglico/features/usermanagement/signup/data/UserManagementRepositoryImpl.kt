package com.fiap.startupone.nutriglico.features.usermanagement.signup.data

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service.UserManagementService
import com.fiap.startupone.nutriglico.features.usermanagement.signup.repository.UserManagementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class UserManagementRepositoryImpl(
    private val userManagementService: UserManagementService
) : UserManagementRepository {

    override suspend fun registerPatient(userRequest: UserRequest): Result<Unit> = handleApiCall {
        userManagementService.registerPatient(userRequest)
    }

    override suspend fun registerNutritionist(userRequest: UserRequest): Result<Unit> = handleApiCall {
        userManagementService.registerNutritionist(userRequest)
    }

    override suspend fun updateUser(userId: String, updatedUserRequest: UserRequest): Result<Unit> = handleApiCall {
        userManagementService.updateUser(userId, updatedUserRequest)
    }

    override suspend fun deleteUser(userId: String): Result<Unit> = handleApiCall {
        userManagementService.deleteUser(userId)
    }

    override suspend fun getUserDetails(userId: String): Result<UserResponse> = handleApiCall {
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