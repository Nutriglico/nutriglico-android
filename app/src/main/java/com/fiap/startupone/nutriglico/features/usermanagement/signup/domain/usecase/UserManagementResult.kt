package com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase

sealed class UserManagementResult<out T> {
    data class Success<out T>(val data: T) : UserManagementResult<T>()
    data class Error(val exception: Exception) : UserManagementResult<Nothing>()
    object ValidationError : UserManagementResult<Nothing>()
}