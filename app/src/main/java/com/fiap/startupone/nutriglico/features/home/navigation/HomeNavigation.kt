package com.fiap.startupone.nutriglico.features.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.screen.GlicemicHistoryScreen
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.screen.GlicemicRecordDetailScreen
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel.GlicemicHistoryViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel.GlicemicRecordDetailViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui.screen.RegisterGlicemicControlScreen
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui.viewmodel.RegisterGlicemicControlViewModel
import com.fiap.startupone.nutriglico.features.home.ui.screen.HomeScreen
import com.fiap.startupone.nutriglico.features.home.ui.viewmodel.HomeViewModel
import com.fiap.startupone.nutriglico.features.menu.ui.screen.MenuScreen
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.screen.EditProfileScreen
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.screen.ProfileScreen
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.EditProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit
) {
    NavHost(
        navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = "home") {
            ExecuteHomeScreen(navController)
        }
        composable(route = "glicemic") {
            ExecuteRegisterGlicemicControlScreen(navController)
        }
        composable(route = "glicemicHistory") {
            ExecuteGlicemicHistoryScreen(navController)
        }
        composable(
            route = "glicemicRecordDetail/{recordId}",
            arguments = listOf(navArgument("recordId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recordId = backStackEntry.arguments?.getString("recordId") ?: return@composable
            ExecuteGlicemicRecordDetailScreen(navController, recordId)
        }
        composable(route = "food") {
            ExecuteMealsScreen()
        }
        composable(route = "menu") {
            ExecuteMenuScreen(navController = navController)
        }
        composable(
            route = "profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
            ExecuteProfileScreen(navController, userId, navigateToLogin)
        }
        composable(route = "editProfile") {
            ExecuteEditProfileScreen(navController)
        }
    }
}

@Composable
private fun ExecuteHomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = koinViewModel()
    HomeScreen(
        viewModel = homeViewModel,
        navController = navController
    )
}

@Composable
fun ExecuteRegisterGlicemicControlScreen(navController: NavController) {
    val viewModel: RegisterGlicemicControlViewModel = koinViewModel()
    RegisterGlicemicControlScreen(
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
fun ExecuteGlicemicHistoryScreen(navController: NavController) {
    val viewModel: GlicemicHistoryViewModel = koinViewModel()
    GlicemicHistoryScreen(
        viewModel = viewModel,
        navController = navController,
        onItemClick = { recordId ->
            navController.navigate("glicemicRecordDetail/$recordId")
        }
    )
}

@Composable
fun ExecuteGlicemicRecordDetailScreen(
    navController: NavController,
    recordId: String
) {
    val viewModel: GlicemicRecordDetailViewModel = koinViewModel()
    GlicemicRecordDetailScreen(
        viewModel = viewModel,
        recordId = recordId,
        navController = navController
    )
}

@Composable
fun ExecuteMealsScreen() {
    // Conteúdo da tela de registro de refeições
}

@Composable
fun ExecuteMenuScreen(navController: NavController) {
    MenuScreen(navController = navController)
}

@Composable
fun ExecuteProfileScreen(
    navController: NavController,
    userId: String,
    navigateToLogin: () -> Unit
) {
    ProfileScreen(
        navController = navController,
        userId = userId,
        navigateToLogin = navigateToLogin
    )
}

@Composable
fun ExecuteEditProfileScreen(navController: NavController) {
    val viewModel: EditProfileViewModel = koinViewModel()
    EditProfileScreen(viewModel = viewModel, navController = navController)
}
