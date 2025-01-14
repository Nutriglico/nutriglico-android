package com.fiap.startupone.nutriglico.commons.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String? = null // Opcional, dependendo do uso
)
