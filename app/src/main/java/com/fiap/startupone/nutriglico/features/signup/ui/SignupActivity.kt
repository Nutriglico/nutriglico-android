package com.fiap.startupone.nutriglico.features.signup.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.features.home.ui.HomeScreen
import com.fiap.startupone.nutriglico.features.signup.viewmodel.SignupViewModel
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