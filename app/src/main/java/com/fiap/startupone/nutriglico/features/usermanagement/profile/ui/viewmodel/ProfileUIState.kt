package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.UserResponse

sealed class ProfileUIState {
    object Idle : ProfileUIState()
    object Loading : ProfileUIState()
    data class Success(val user: UserResponse) : ProfileUIState()
    data class Error(val message: String) : ProfileUIState()
}