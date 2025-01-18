package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.ProfileUserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.ProfileResult
import com.fiap.startupone.nutriglico.features.usermanagement.profile.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditProfileUIState>(EditProfileUIState.Idle)
    val uiState: StateFlow<EditProfileUIState> get() = _uiState

    fun updateUser(name: String, email: String, cpf: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = EditProfileUIState.Loading
            try {
                val userId = retrieveUserId()
                val profileUserRequest = ProfileUserRequest(name, email, cpf)

                when (val result = updateUserUseCase(userId, profileUserRequest)) {
                    is ProfileResult.Success -> {
                        onSuccess()
                        _uiState.value = EditProfileUIState.Success
                    }
                    is ProfileResult.Error -> {
                        _uiState.value = EditProfileUIState.Error(result.exception.message ?: "Erro desconhecido")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = EditProfileUIState.Error("Erro inesperado: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = EditProfileUIState.Idle
    }

    private fun retrieveUserId(): String {
        // Simulação para recuperar o ID do usuário
        return "1"
    }
}
