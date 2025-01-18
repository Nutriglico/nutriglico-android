package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.repository

import android.util.Log
import com.fiap.startupone.nutriglico.commons.util.FeatureFlags
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service.MockProfileService
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.service.ProfileService

class ProfileRepositoryImpl(
    profileService: ProfileService
) : ProfileRepository {

    private val actualService: ProfileService = if (FeatureFlags.USE_MOCKS) {
        MockProfileService()
    } else {
        profileService
    }

    override suspend fun getUserDetails(userId: String): Result<ProfileUserResponse> {
        Log.d("API Request", "Fetching user details for user ID: $userId")
        return try {
            val response = actualService.getUserDetails(userId)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(userId: String, profileUserRequest: ProfileUserRequest): Result<Unit> {
        Log.d("API Request", "Updating user details for user ID: $userId")
        return try {
            val response = actualService.updateUser(userId, profileUserRequest)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(userId: String): Result<Unit> {
        Log.d("API Request", "Deleting user with ID: $userId")
        return try {
            val response = actualService.deleteUser(userId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
