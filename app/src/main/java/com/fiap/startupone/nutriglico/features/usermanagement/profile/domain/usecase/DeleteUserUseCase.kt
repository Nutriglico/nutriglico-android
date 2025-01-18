package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository

class DeleteUserUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String): ProfileResult<Unit> {
        if (userId.isBlank()) {
            return ProfileResult.Error(IllegalArgumentException("User ID não pode ser vazio"))
        }

        return try {
            repository.deleteUser(userId).fold(
                onSuccess = { ProfileResult.Success(Unit) },
                onFailure = { ProfileResult.Error(it) }
            )
        } catch (e: Exception) {
            ProfileResult.Error(e)
        }
    }
}
