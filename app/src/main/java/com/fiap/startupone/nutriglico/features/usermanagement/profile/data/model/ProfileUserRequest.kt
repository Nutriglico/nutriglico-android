package com.fiap.startupone.nutriglico.features.usermanagement.profile.data.model

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
