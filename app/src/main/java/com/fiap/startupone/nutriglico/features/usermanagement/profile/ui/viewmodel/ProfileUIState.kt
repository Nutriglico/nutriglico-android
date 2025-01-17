package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserResponse

sealed class ProfileUIState {
    object Idle : ProfileUIState()
    object Loading : ProfileUIState()
    data class Success(val user: ProfileUserResponse) : ProfileUIState()
    data class Error(val message: String) : ProfileUIState()
}