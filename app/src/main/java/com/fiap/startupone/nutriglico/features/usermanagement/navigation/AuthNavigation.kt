package com.fiap.startupone.nutriglico.features.usermanagement.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.UserAuthScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.UserSignUpScreen
import com.fiap.startupone.nutriglico.features.usermanagement.auth.viewmodel.UserAuthViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.UserSignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthNavigation(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        composable(AuthScreen.Login.route) {
            val viewModel: UserAuthViewModel = koinViewModel()
            UserAuthScreen(
                viewModel = viewModel,
                onUserAuthSuccess = onAuthSuccess,
                onSignupClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotPasswordClick = { /* Implementação futura */ }
            )
        }

        composable(AuthScreen.SignUp.route) {
            val viewModel: UserSignUpViewModel = koinViewModel()
            UserSignUpScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSignUpSuccess = onAuthSuccess
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object SignUp : AuthScreen("signup")
}