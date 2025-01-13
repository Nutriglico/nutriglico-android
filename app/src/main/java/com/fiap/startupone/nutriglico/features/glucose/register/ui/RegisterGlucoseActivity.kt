package com.fiap.startupone.nutriglico.features.glucose.register.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fiap.startupone.nutriglico.features.glucose.register.viewmodel.RegisterGlucoseViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel

class RegisterGlucoseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val viewModel: RegisterGlucoseViewModel = koinViewModel()
                RegisterGlucoseScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }

}
