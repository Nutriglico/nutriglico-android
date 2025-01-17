package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui.viewmodel.RegisterGlicemicControlViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterGlicemicControlActivity : ComponentActivity() {

    private val viewModel: RegisterGlicemicControlViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val navController = rememberNavController()
                //val viewModel: RegisterGlicemicControlViewModel = koinViewModel()
                RegisterGlicemicControlScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }

}
