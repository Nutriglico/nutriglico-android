package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model

data class RegisterGlicemicLevelRequest(
    val type: String = "RANDOM",
    val level: Int,
    val registerDate: String? = null
)
