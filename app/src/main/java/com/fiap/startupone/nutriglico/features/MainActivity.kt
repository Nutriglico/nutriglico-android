package com.fiap.startupone.nutriglico.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fiap.startupone.nutriglico.commons.ui.BottomNavigationBar
import com.fiap.startupone.nutriglico.commons.ui.ExitAppDialog
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
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                MainScreen(this)
            }
        }
    }
}

@Composable
fun MainScreen(context: MainActivity) {
    val navController = rememberNavController()

    configBackHandler(context)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = "home") {
                executeHomeScreen(navController)
            }
            composable(route = "glicemic") {
                executeRegisterGlicemicControlScreen(navController)
            }
            composable(route = "glicemicHistory") {
                executeGlicemicHistoryScreen(navController)
            }
            composable(
                route = "glicemicRecordDetail/{recordId}",
                arguments = listOf(navArgument("recordId") { type = NavType.StringType })
            ) { backStackEntry ->
                val recordId = backStackEntry.arguments?.getString("recordId") ?: return@composable
                executeGlicemicRecordDetailScreen(navController, recordId)
            }
            composable(route = "food") {
                executeMealsScreen()
            }
            composable(route = "menu") {
                executeMenuScreen(navController = navController)
            }
            composable(
                route = "profile/{userId}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
                executeProfileScreen(navController, userId)
            }
            composable(route = "editProfile") {
                executeEditProfileScreen(navController)
            }
        }

    }
}

@Composable
private fun configBackHandler(context: MainActivity) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        ExitAppDialog(
            onConfirm = { context.finish() },
            onDismiss = { showExitDialog = false }
        )
    }
}

@Composable
private fun executeHomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = koinViewModel()
    HomeScreen(viewModel = homeViewModel, navController = navController)
}

@Composable
fun executeRegisterGlicemicControlScreen(navController: NavController) {
    val viewModel: RegisterGlicemicControlViewModel = koinViewModel()
    RegisterGlicemicControlScreen(
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
fun executeGlicemicHistoryScreen(navController: NavController) {
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
fun executeGlicemicRecordDetailScreen(navController: NavController, recordId: String) {
    val viewModel: GlicemicRecordDetailViewModel = koinViewModel()
    GlicemicRecordDetailScreen(
        viewModel = viewModel,
        recordId = recordId,
        navController = navController
    )
}

@Composable
fun executeMealsScreen() {
    // Conteúdo da tela de registro de refeições
}

@Composable
fun executeMenuScreen(navController: NavController) {
    MenuScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NutriGlicoTheme {
        val context = LocalContext.current as MainActivity
        MainScreen(context)
    }
}

@Composable
fun executeProfileScreen(navController: NavController, userId: String) {
    ProfileScreen(navController = navController, userId = userId)
}

@Composable
fun executeEditProfileScreen(navController: NavController) {
    val viewModel: EditProfileViewModel = koinViewModel()
    EditProfileScreen(viewModel = viewModel, navController = navController)
}