package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import android.util.Log
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse

class GetUserDetailsUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String): ProfileResult<ProfileUserResponse> {
        if (userId.isBlank()) {
            return ProfileResult.Error(IllegalArgumentException("User ID não pode ser vazio"))
        }

        Log.d("API Request", "Fetching user details for user ID: $userId")
        return try {
            val result = repository.getUserDetails(userId)
            result.fold(
                onSuccess = {
                    Log.d("API Success", "Response: $it")
                    ProfileResult.Success(it)
                },
                onFailure = {
                    Log.e("API Error", "Response: ${it.message}")
                    ProfileResult.Error(it as Exception)
                }
            )
        } catch (e: Exception) {
            Log.e("API Error", "Exception: ${e.message}", e)
            ProfileResult.Error(e)
        }
    }
}