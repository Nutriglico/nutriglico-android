package com.fiap.startupone.nutriglico.features.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.SplashScreenActivity
import com.fiap.startupone.nutriglico.commons.ui.BottomNavigationBar
import com.fiap.startupone.nutriglico.commons.ui.ExitAppDialog
import com.fiap.startupone.nutriglico.features.home.navigation.HomeNavigation
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NutriGlicoTheme {
                val navController = rememberNavController()

                ConfigBackHandler(this)

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController)
                    },
                    content = { paddingValues ->
                        HomeNavigation(
                            navController = navController,
                            paddingValues = paddingValues,
                            navigateToLogin = { navigateToLogin() }
                        )
                    }
                )
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finish()
    }
}

@Composable
private fun ConfigBackHandler(context: MainActivity) {
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