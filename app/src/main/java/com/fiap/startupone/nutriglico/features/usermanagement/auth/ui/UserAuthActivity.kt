package com.fiap.startupone.nutriglico.features.usermanagement.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fiap.startupone.nutriglico.features.MainActivity
import com.fiap.startupone.nutriglico.features.usermanagement.auth.viewmodel.UserAuthViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.SignupActivity
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel

class UserAuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val viewModel: UserAuthViewModel = koinViewModel()
                UserAuthScreen(
                    viewModel = viewModel,
                    onUserAuthSuccess = { navigateToHome() },
                    onSignupClick = { navigateToSignup() },
                    onForgotPasswordClick = { /* Implementação futura */ }
                )
            }
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToSignup() {
        startActivity(Intent(this, SignupActivity::class.java))
    }

}
