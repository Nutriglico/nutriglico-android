package com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase

sealed class ProfileResult<out T> {
    data class Success<out T>(val data: T) : ProfileResult<T>()
    data class Error(val exception: Throwable) : ProfileResult<Nothing>()
}