package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

sealed class EditProfileUIState {
    object Idle : EditProfileUIState()
    object Loading : EditProfileUIState()
    object Success : EditProfileUIState()
    data class Error(val message: String) : EditProfileUIState()
}