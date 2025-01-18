package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.SignUpUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.UserManagementRepository

class RegisterNutritionistUseCase(private val repository: UserManagementRepository) {
    suspend operator fun invoke(signUpUserRequest: SignUpUserRequest): UserManagementResult<Unit> {
        if (signUpUserRequest.name.isBlank() || signUpUserRequest.email.isBlank() || signUpUserRequest.password.isBlank() || signUpUserRequest.licenseNumber.isNullOrBlank()) {
            return UserManagementResult.ValidationError
        }
        return try {
            val result = repository.registerNutritionist(signUpUserRequest)
            result.fold(
                onSuccess = { UserManagementResult.Success(Unit) },
                onFailure = { UserManagementResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            UserManagementResult.Error(e)
        }
    }
}