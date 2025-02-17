package com.fiap.startupone.nutriglico.features.usermanagement

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.features.home.MainActivity
import com.fiap.startupone.nutriglico.features.usermanagement.navigation.AuthNavigation
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val navController = rememberNavController()

                AuthNavigation(
                    navController = navController,
                    onAuthSuccess = { navigateToMain() }
                )
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}