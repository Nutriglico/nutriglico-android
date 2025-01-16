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
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.GlicemicHistoryScreen
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.GlicemicRecordDetailScreen
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel.GlicemicHistoryViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel.GlicemicRecordDetailViewModel
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui.RegisterGlicemicControlScreen
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel.RegisterGlicemicControlViewModel
import com.fiap.startupone.nutriglico.features.home.ui.HomeScreen
import com.fiap.startupone.nutriglico.features.home.viewmodel.HomeViewModel
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
            composable("home") { executeHomeScreen(navController) }
            composable("glicemic") { executeRegisterGlicemicControlScreen(navController) }
            composable("glicemicHistory") { executeGlicemicHistoryScreen(navController) }
            composable(
                route = "glicemicRecordDetail/{recordId}",
                arguments = listOf(navArgument("recordId") { type = NavType.StringType })
            ) { backStackEntry ->
                val recordId = backStackEntry.arguments?.getString("recordId") ?: return@composable
                executeGlicemicRecordDetailScreen(navController, recordId)
            }
            composable("inserir") { executeMeasurementsScreen() }
            composable("refeicao") { executeMealsScreen() }
            composable("menu") { executeMenuScreen() }
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
fun executeMeasurementsScreen() {
    // Conteúdo da tela de registro de medições
}

@Composable
fun executeMealsScreen() {
    // Conteúdo da tela de registro de refeições
}

@Composable
fun executeMenuScreen() {
    // Conteúdo da tela de menu
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NutriGlicoTheme {
        val context = LocalContext.current as MainActivity
        MainScreen(context)
    }
}
