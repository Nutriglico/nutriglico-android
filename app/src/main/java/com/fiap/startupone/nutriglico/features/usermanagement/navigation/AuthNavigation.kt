package com.fiap.startupone.nutriglico.features.usermanagement.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.screen.UserAuthScreen
import com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.viewmodel.UserAuthViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.screen.RegisterNutritionistScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.screen.RegisterPatientScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.screen.SelectProfileScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.screen.UserSignUpScreen
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.viewmodel.UserSignUpViewModel
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
            ExecuteUserAuthScreen(navController, onAuthSuccess)
        }
        composable(AuthScreen.SignUp.route) {
            ExecuteUserSignUpScreen(navController, onAuthSuccess)
        }
        composable(AuthScreen.SelectProfile.route) {
            ExecuteSelectProfileScreen(navController)
        }
        composable(AuthScreen.RegisterPatient.route) {
            ExecuteRegisterPatientScreen(navController, onAuthSuccess)
        }
        composable(AuthScreen.RegisterNutritionist.route) {
            ExecuteRegisterNutritionistScreen(navController, onAuthSuccess)
        }
    }
}

@Composable
fun ExecuteUserAuthScreen(
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
fun ExecuteUserSignUpScreen(
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
fun ExecuteSelectProfileScreen(
    navController: NavHostController
) {
    SelectProfileScreen(
        navController = navController,
        onPatientClick = { navController.navigate(AuthScreen.RegisterPatient.route) },
        onNutritionistClick = { navController.navigate(AuthScreen.RegisterNutritionist.route) }
    )
}

@Composable
fun ExecuteRegisterPatientScreen(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    RegisterPatientScreen(
        navController = navController,
        onRegisterSuccess = { onAuthSuccess() }
    )
}

@Composable
fun ExecuteRegisterNutritionistScreen(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    RegisterNutritionistScreen(
        navController = navController,
        onRegisterSuccess = { onAuthSuccess() }
    )
}
