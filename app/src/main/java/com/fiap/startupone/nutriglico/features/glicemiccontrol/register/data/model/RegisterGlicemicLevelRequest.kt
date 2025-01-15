package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model

import com.google.gson.annotations.SerializedName

data class RegisterGlicemicLevelRequest(
    @SerializedName("type") val type: String,
    @SerializedName("level") val level: Int,
    @SerializedName("registerDate") val registerDate: String? = null
)
