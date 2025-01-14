package com.fiap.startupone.nutriglico.features.usermanagement.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.usermanagement.auth.domain.usecase.UserAuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserAuthViewModel(
    private val userAuthUseCase: UserAuthUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserAuthUiState>(UserAuthUiState.Idle)
    val uiState: StateFlow<UserAuthUiState> get() = _uiState

    fun authenticate(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UserAuthUiState.Loading
            try {
                val result = userAuthUseCase.execute(email, password)
                if (result.isSuccess) {
                    _uiState.value = UserAuthUiState.Success
                } else {
                    _uiState.value = UserAuthUiState.Error("Authentication failed")
                }
            } catch (e: Exception) {
                _uiState.value = UserAuthUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class UserAuthUiState {
    object Idle : UserAuthUiState()
    object Loading : UserAuthUiState()
    object Success : UserAuthUiState()
    data class Error(val errorMessage: String) : UserAuthUiState()
}
