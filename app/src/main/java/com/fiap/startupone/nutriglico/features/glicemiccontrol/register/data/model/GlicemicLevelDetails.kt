package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model

import com.google.gson.annotations.SerializedName

data class GlicemicLevelDetails(
    @SerializedName("id") val id: String,
    @SerializedName("value") val value: Int,
    @SerializedName("condition") val condition: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("notes") val notes: String? = null
)