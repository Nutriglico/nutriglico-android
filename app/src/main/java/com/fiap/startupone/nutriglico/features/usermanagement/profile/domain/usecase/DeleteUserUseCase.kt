package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository

class DeleteUserUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(userId: String): ProfileResult<Unit> {
        return try {
            val result = repository.deleteUser(userId)
            result.fold(
                onSuccess = { ProfileResult.Success(Unit) },
                onFailure = { ProfileResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            ProfileResult.Error(e)
        }
    }
}
