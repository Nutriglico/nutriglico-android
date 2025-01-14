package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel.RegisterGlicemicControlViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel

class RegisterGlicemicControlActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val navController = rememberNavController()
                val viewModel: RegisterGlicemicControlViewModel = koinViewModel()
                RegisterGlicemicControlScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }

}
