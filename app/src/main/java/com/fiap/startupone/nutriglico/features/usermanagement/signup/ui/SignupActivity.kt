package com.fiap.startupone.nutriglico.features.usermanagement.signup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.SignupViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val viewModel: SignupViewModel = koinViewModel()
                SignupScreen(viewModel = viewModel, onBack = { finish() })
            }
        }
    }
}