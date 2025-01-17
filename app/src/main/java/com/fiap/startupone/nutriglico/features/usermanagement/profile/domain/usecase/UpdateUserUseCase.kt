package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository

class UpdateUserUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String, profileUserRequest: ProfileUserRequest): ProfileResult<Unit> {
        return try {
            val result = repository.updateUser(userId, profileUserRequest)
            result.fold(
                onSuccess = { ProfileResult.Success(Unit) },
                onFailure = { ProfileResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            ProfileResult.Error(e)
        }
    }
}