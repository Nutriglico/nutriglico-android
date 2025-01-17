package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.repository.UserManagementRepository

class RegisterNutritionistUseCase(private val repository: UserManagementRepository) {
    suspend operator fun invoke(userRequest: UserRequest): UserManagementResult<Unit> {
        if (userRequest.name.isBlank() || userRequest.email.isBlank() || userRequest.password.isBlank() || userRequest.licenseNumber.isNullOrBlank()) {
            return UserManagementResult.ValidationError
        }
        return try {
            val result = repository.registerNutritionist(userRequest)
            result.fold(
                onSuccess = { UserManagementResult.Success(Unit) },
                onFailure = { UserManagementResult.Error(it as Exception) }
            )
        } catch (e: Exception) {
            UserManagementResult.Error(e)
        }
    }
}