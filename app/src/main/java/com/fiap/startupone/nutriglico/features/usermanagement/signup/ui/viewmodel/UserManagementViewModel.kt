package com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserManagementRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.RegisterNutritionistUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.RegisterPatientUseCase
import com.fiap.startupone.nutriglico.features.usermanagement.signup.domain.usecase.UserManagementResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserManagementViewModel(
    private val registerPatientUseCase: RegisterPatientUseCase,
    private val registerNutritionistUseCase: RegisterNutritionistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserManagementUIState>(UserManagementUIState.Idle)
    val uiState: StateFlow<UserManagementUIState> get() = _uiState

    fun registerPatient(userManagementRequest: UserManagementRequest) {
        viewModelScope.launch {
            _uiState.value = UserManagementUIState.Loading
            val result = registerPatientUseCase(userManagementRequest)
            _uiState.value = when (result) {
                is UserManagementResult.Success -> UserManagementUIState.Success
                is UserManagementResult.Error -> UserManagementUIState.Error(result.exception.message ?: "Erro desconhecido")
                is UserManagementResult.ValidationError -> UserManagementUIState.ValidationError("Dados inválidos. Preencha todos os campos obrigatórios.")
            }
        }
    }

    fun registerNutritionist(userManagementRequest: UserManagementRequest) {
        viewModelScope.launch {
            _uiState.value = UserManagementUIState.Loading
            val result = registerNutritionistUseCase(userManagementRequest)
            _uiState.value = when (result) {
                is UserManagementResult.Success -> UserManagementUIState.Success
                is UserManagementResult.Error -> UserManagementUIState.Error(result.exception.message ?: "Erro desconhecido")
                is UserManagementResult.ValidationError -> UserManagementUIState.ValidationError("Dados inválidos. Preencha todos os campos obrigatórios.")
            }
        }
    }
}

sealed class UserManagementUIState {
    object Idle : UserManagementUIState()
    object Loading : UserManagementUIState()
    object Success : UserManagementUIState()
    data class Error(val message: String) : UserManagementUIState()
    data class ValidationError(val message: String) : UserManagementUIState()
}