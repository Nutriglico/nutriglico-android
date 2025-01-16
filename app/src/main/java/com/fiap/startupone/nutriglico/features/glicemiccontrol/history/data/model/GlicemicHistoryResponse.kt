package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model

import com.google.gson.annotations.SerializedName

data class GlicemicHistoryResponse(
    @SerializedName("id") val id: String,
    @SerializedName("level") val level: Int,
    @SerializedName("registerDate") val registerDate: String,
    @SerializedName("type") val type: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("colorRate") val colorRate: String
)
