package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.SignUpUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.repository.UserManagementRepository

class RegisterPatientUseCase(private val repository: UserManagementRepository) {
    suspend operator fun invoke(signUpUserRequest: SignUpUserRequest): UserManagementResult<Unit> {
        if (signUpUserRequest.name.isBlank() || signUpUserRequest.email.isBlank() || signUpUserRequest.password.isBlank()) {
            return UserManagementResult.ValidationError
        }
        return try {
            val result = repository.registerPatient(signUpUserRequest)
            result.fold(
                onSuccess = { UserManagementResult.Success(Unit) },
                onFailure = { UserManagementResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            UserManagementResult.Error(e)
        }
    }
}
