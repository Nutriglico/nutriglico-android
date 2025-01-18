package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.ProfileRepository

class UpdateUserUseCase(private val repository: ProfileRepository) {

    companion object {
        const val ERROR_USER_ID = "User ID não pode ser vazio"
        const val ERROR_INVALID_USER = "Dados do usuário inválidos"
    }

    suspend operator fun invoke(userId: String, profileUserRequest: ProfileUserRequest): ProfileResult<Unit> {
        if (userId.isBlank()) {
            return ProfileResult.Error(IllegalArgumentException(ERROR_USER_ID))
        }

        if (!isValidProfileRequest(profileUserRequest)) {
            return ProfileResult.Error(IllegalArgumentException(ERROR_INVALID_USER))
        }

        return try {
            repository.updateUser(userId, profileUserRequest).fold(
                onSuccess = { ProfileResult.Success(Unit) },
                onFailure = { ProfileResult.Error(it) }
            )
        } catch (e: Exception) {
            ProfileResult.Error(e)
        }
    }

    private fun isValidProfileRequest(request: ProfileUserRequest): Boolean {
        return request.name.isNotBlank() && request.email.isNotBlank() && request.cpf.isNotBlank()
    }
}
