package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeProfileViewModel(initialState: ProfileUIState) : ProfileViewModelContract {

    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<ProfileUIState> get() = _uiState

    var loadUserDetailsCalled: Boolean = false
    var lastUserIdLoaded: String? = null
    var deleteAccountCalled: Boolean = false
    var lastDeletedUserId: String? = null
    var deleteAccountOnSuccess: (() -> Unit)? = null
    var deleteAccountOnError: ((String) -> Unit)? = null

    override fun loadUserDetails(userId: String) {
        loadUserDetailsCalled = true
        lastUserIdLoaded = userId
    }

    override fun deleteAccount(userId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        deleteAccountCalled = true
        lastDeletedUserId = userId
        deleteAccountOnSuccess = onSuccess
        deleteAccountOnError = onError
    }

    override fun showError(message: String) {
        _uiState.value = ProfileUIState.Error(message)
    }

    fun setState(state: ProfileUIState) {
        _uiState.value = state
    }

    fun triggerDeleteAccountSuccess() {
        deleteAccountOnSuccess?.invoke()
    }

    fun triggerDeleteAccountError(message: String) {
        deleteAccountOnError?.invoke(message)
    }
}
