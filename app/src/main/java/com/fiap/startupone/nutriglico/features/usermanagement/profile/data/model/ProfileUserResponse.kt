package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model

import com.google.gson.annotations.SerializedName

data class ProfileUserResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
