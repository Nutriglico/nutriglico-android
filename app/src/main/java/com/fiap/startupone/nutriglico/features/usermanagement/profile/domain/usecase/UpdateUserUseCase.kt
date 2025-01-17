package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository

class UpdateUserUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String, userRequest: UserRequest): ProfileResult<Unit> {
        return try {
            val result = repository.updateUser(userId, userRequest)
            result.fold(
                onSuccess = { ProfileResult.Success(Unit) },
                onFailure = { ProfileResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            ProfileResult.Error(e)
        }
    }
}