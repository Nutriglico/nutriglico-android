package com.fiap.startupone.nutriglico.features.usermanagement.signup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.UserSignUpViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel

class UserSignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val viewModel: UserSignUpViewModel = koinViewModel()
                UserSignUpScreen(viewModel = viewModel, onBack = { finish() })
            }
        }
    }
}