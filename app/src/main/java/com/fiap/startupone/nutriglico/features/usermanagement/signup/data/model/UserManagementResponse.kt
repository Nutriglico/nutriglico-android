package com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model

import com.google.gson.annotations.SerializedName

// Classe para representar as respostas
data class UserManagementResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("birth_date") val birthDate: String? = null, // Opcional para nutricionistas
    @SerializedName("license_number") val licenseNumber: String? = null // Apenas para nutricionistas
)