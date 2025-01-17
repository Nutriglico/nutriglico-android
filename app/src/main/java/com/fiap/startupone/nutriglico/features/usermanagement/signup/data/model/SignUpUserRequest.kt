package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model

import com.google.gson.annotations.SerializedName

// Classe para representar as requisições
data class SignUpUserRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("birth_date") val birthDate: String? = null, // Opcional para nutricionistas
    @SerializedName("license_number") val licenseNumber: String? = null // Apenas para nutricionistas
)
