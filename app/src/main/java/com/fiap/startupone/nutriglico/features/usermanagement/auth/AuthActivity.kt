package com.fiap.startupone.nutriglico.features.usermanagement.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.features.MainActivity
import com.fiap.startupone.nutriglico.features.usermanagement.auth.navigation.AuthNavigation
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val navController = rememberNavController()

                AuthNavigation(
                    navController = navController,
                    onAuthSuccess = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}