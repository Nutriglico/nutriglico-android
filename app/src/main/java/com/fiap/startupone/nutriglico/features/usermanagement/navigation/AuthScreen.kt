package com.fiap.startupone.nutriglico.features.usermanagement.navigation

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object SignUp : AuthScreen("signup")
}