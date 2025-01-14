package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model

import com.google.gson.annotations.SerializedName

data class GlicemicLevelResponse(
    @SerializedName("id") val id: String,
    @SerializedName("value") val value: Int,
    @SerializedName("timestamp") val timestamp: String
)