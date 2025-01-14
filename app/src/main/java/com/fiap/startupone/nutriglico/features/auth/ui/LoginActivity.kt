package com.fiap.startupone.nutriglico.features.auth.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fiap.startupone.nutriglico.features.home.ui.HomeActivity
import com.fiap.startupone.nutriglico.features.signup.ui.SignupActivity
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                LoginScreen(
                    onLogin = { email, password ->
                        if (validateCredentials(email, password)) {
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Email ou senha inválidos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onForgotPassword = {
                        // Navegar para a tela de "Esqueci Minha Senha"
                    },
                    onSignUp = {
                        val intent = Intent(this, SignupActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex) && password.length >= 6
    }
}
