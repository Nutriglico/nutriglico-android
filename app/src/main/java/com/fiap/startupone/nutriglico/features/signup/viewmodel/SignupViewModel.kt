package com.fiap.startupone.nutriglico.features.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.startupone.nutriglico.commons.model.User
import com.fiap.startupone.nutriglico.features.signup.domain.usecase.CreateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val createUserUseCase: CreateUserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> get() = _uiState

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading
            val result = createUserUseCase.execute(User(name, email, password))
            _uiState.value = if (result.isSuccess) {
                SignupUiState.Success("Usuário cadastrado com sucesso!")
            } else {
                SignupUiState.Error(result.exceptionOrNull()?.message ?: "Erro desconhecido")
            }
        }
    }
}

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val message: String) : SignupUiState()
    data class Error(val errorMessage: String) : SignupUiState()
}
