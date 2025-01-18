package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository

import com.fiap.startupone.nutriglico.commons.util.FeatureFlags
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserManagementRepository
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementResponse
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service.MockUserManagementService
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.service.UserManagementService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserManagementRepositoryImpl(
    userManagementService: UserManagementService
) : UserManagementRepository {

    private val actualService: UserManagementService = if (FeatureFlags.USE_MOCKS) {
        MockUserManagementService()
    } else {
        userManagementService
    }

    override suspend fun registerPatient(userManagementRequest: UserManagementRequest): Result<Unit> {
        return handleApiCall { actualService.registerPatient(userManagementRequest) }
    }

    override suspend fun registerNutritionist(userManagementRequest: UserManagementRequest): Result<Unit> {
        return handleApiCall { actualService.registerNutritionist(userManagementRequest) }
    }

    override suspend fun updateUser(userId: String, updatedUserManagementRequest: UserManagementRequest): Result<Unit> {
        return handleApiCall { actualService.updateUser(userId, updatedUserManagementRequest) }
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        return handleApiCall { actualService.deleteUser(userId) }
    }

    override suspend fun getUserDetails(userId: String): Result<UserManagementResponse> {
        return handleApiCall { actualService.getUserDetails(userId) }
    }

    private suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
