package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserManagementRepository

class RegisterPatientUseCase(private val repository: UserManagementRepository) {
    suspend operator fun invoke(userManagementRequest: UserManagementRequest): UserManagementResult<Unit> {
        if (userManagementRequest.name.isBlank() || userManagementRequest.email.isBlank() || userManagementRequest.password.isBlank()) {
            return UserManagementResult.ValidationError
        }
        return try {
            val result = repository.registerPatient(userManagementRequest)
            result.fold(
                onSuccess = { UserManagementResult.Success(Unit) },
                onFailure = { UserManagementResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            UserManagementResult.Error(e)
        }
    }
}
