package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface ProfileViewModelContract {
    val uiState: StateFlow<ProfileUIState>
    fun loadUserDetails(userId: String)
    fun deleteAccount(userId: String, onSuccess: () -> Unit, onError: (String) -> Unit)
    fun showError(message: String)
}