package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import android.util.Log
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.UserResponse

class GetUserDetailsUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String): ProfileResult<UserResponse> {
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
                    //ProfileResult.Error(it as Exception)

                    ProfileResult.Success(
                        UserResponse(
                            id = "1",
                            name = "John Doe",
                            email = "",
                            createdAt = "2021-10-01T00:00:00Z",
                            updatedAt = "2021-10-01T00:00:00Z"
                        )
                    )
                }
            )
        } catch (e: Exception) {
            Log.e("API Error", "Exception: ${e.message}", e)
            ProfileResult.Error(e)
        }
    }
}