package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.DeleteUserUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.GetUserDetailsUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.ProfileResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUIState>(ProfileUIState.Idle)
    val uiState: StateFlow<ProfileUIState> get() = _uiState

    fun loadUserDetails(userId: String) {
        Log.d("API Request", "Loading user details for user ID: $userId")
        viewModelScope.launch {
            _uiState.value = ProfileUIState.Loading
            val result = getUserDetailsUseCase(userId)
            _uiState.value = when (result) {
                is ProfileResult.Success -> ProfileUIState.Success(result.data)
                is ProfileResult.Error -> ProfileUIState.Error(result.exception.message ?: "Erro desconhecido")
            }
        }
    }

    fun deleteAccount(userId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d("API Request", "Deleting account for user ID: $userId")
        viewModelScope.launch {
            when (val result = deleteUserUseCase(userId)) {
                is ProfileResult.Success -> onSuccess()
                is ProfileResult.Error -> onError(result.exception.message ?: "Erro desconhecido")
            }
        }
    }

    fun showError(message: String) {
        _uiState.value = ProfileUIState.Error(message)
    }

}