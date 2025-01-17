package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model.UserRequest
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

    fun updateUser(userId: String, userRequest: UserRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _uiState.value = EditProfileUIState.Loading
            val result = updateUserUseCase(userId, userRequest)
            _uiState.value = when (result) {
                is ProfileResult.Success -> {
                    onSuccess()
                    EditProfileUIState.Success
                }
                is ProfileResult.Error -> {
                    onError(result.exception.message ?: "Erro desconhecido")
                    EditProfileUIState.Error(result.exception.message ?: "Erro desconhecido")
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = EditProfileUIState.Idle
    }
}
