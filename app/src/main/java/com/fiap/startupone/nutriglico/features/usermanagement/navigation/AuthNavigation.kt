package com.fiap.startupone.nutriglico.features.usermanagement.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.UserAuthScreen
import com.fiap.startupone.nutriglico.features.usermanagement.auth.viewmodel.UserAuthViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.RegisterNutritionistScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.RegisterPatientScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.SelectProfileScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.UserSignUpScreen
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
            executeUserAuthScreen(navController, onAuthSuccess)
        }
        composable(AuthScreen.SignUp.route) {
            executeUserSignUpScreen(navController, onAuthSuccess)
        }
        composable(AuthScreen.SelectProfile.route) {
            executeSelectProfileScreen(navController)
        }
        composable(AuthScreen.RegisterPatient.route) {
            executeRegisterPatientScreen(navController, onAuthSuccess)
        }
        composable(AuthScreen.RegisterNutritionist.route) {
            executeRegisterNutritionistScreen(navController, onAuthSuccess)
        }
    }
}

@Composable
fun executeUserAuthScreen(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    val viewModel: UserAuthViewModel = koinViewModel()
    UserAuthScreen(
        viewModel = viewModel,
        onUserAuthSuccess = onAuthSuccess,
        onSignupClick = {
            navController.navigate(AuthScreen.SelectProfile.route)
        },
        onForgotPasswordClick = { /* Implementação futura */ }
    )
}

@Composable
fun executeUserSignUpScreen(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    // Layout do cadastro do paciente
    // Campos obrigatórios: Nome, Email, Senha, Data de Nascimento
    val viewModel: UserSignUpViewModel = koinViewModel()
    UserSignUpScreen(
        viewModel = viewModel,
        onBack = { navController.popBackStack() },
        onSignUpSuccess = onAuthSuccess
    )
}

@Composable
fun executeSelectProfileScreen(
    navController: NavHostController
) {
    SelectProfileScreen(
        navController = navController,
        onPatientClick = { navController.navigate(AuthScreen.RegisterPatient.route) },
        onNutritionistClick = { navController.navigate(AuthScreen.RegisterNutritionist.route) }
    )
}

@Composable
fun executeRegisterPatientScreen(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    RegisterPatientScreen(
        navController = navController,
        onRegisterSuccess = { onAuthSuccess() }
    )
}

@Composable
fun executeRegisterNutritionistScreen(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    RegisterNutritionistScreen(
        navController = navController,
        onRegisterSuccess = { onAuthSuccess() }
    )
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object SignUp : AuthScreen("signup")
    object SelectProfile : AuthScreen("selectProfile")
    object RegisterPatient : AuthScreen("registerPatient")
    object RegisterNutritionist : AuthScreen("registerNutritionist")
}