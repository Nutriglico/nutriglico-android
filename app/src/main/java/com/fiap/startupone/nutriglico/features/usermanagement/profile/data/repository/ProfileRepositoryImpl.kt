package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.repository

import android.util.Log
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service.ProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileRepositoryImpl(
    private val profileService: ProfileService
) : ProfileRepository {

    override suspend fun getUserDetails(userId: String): Result<ProfileUserResponse> = handleApiCall {
        Log.d("API Request", "Fetching user details for user ID: $userId")
        profileService.getUserDetails(userId)
    }

    override suspend fun updateUser(userId: String, profileUserRequest: ProfileUserRequest): Result<Unit> = handleApiCall {
        Log.d("API Request", "Updating user details for user ID: $userId")
        profileService.updateUser(userId, profileUserRequest)
    }

    override suspend fun deleteUser(userId: String): Result<Unit> = handleApiCall {
        Log.d("API Request", "Deleting user with ID: $userId")
        profileService.deleteUser(userId)
    }

    private suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Log.d("API Success", "Response: ${response.body()}")
                    Result.success(response.body()!!)
                } else {
                    Log.e("API Error", "Response: ${response.code()} - ${response.message()}")
                    Result.failure(Exception("Erro: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}
