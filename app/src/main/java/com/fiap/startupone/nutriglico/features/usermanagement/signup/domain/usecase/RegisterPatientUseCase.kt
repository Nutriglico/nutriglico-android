package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.repository.UserManagementRepository

class RegisterPatientUseCase(private val repository: UserManagementRepository) {
    suspend operator fun invoke(userRequest: UserRequest): UserManagementResult<Unit> {
        if (userRequest.name.isBlank() || userRequest.email.isBlank() || userRequest.password.isBlank()) {
            return UserManagementResult.ValidationError
        }
        return try {
            val result = repository.registerPatient(userRequest)
            result.fold(
                onSuccess = { UserManagementResult.Success(Unit) },
                onFailure = { UserManagementResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            UserManagementResult.Error(e)
        }
    }
}
