package com.fiap.startupone.nutriglico.features.auth.viewmodel

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    fun login(email: String, password: String) {
        // Lógica de autenticação (exemplo simples)
        if (email.isNotEmpty() && password.isNotEmpty()) {
            println("Login com sucesso!")
        } else {
            println("Email ou senha inválidos")
        }
    }
}
