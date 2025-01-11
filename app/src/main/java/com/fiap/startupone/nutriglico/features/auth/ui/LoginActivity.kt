package com.fiap.startupone.nutriglico.features.auth.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                LoginScreen(
                    onLogin = { email, password ->
                        // Implementação do login
                    },
                    onForgotPassword = {
                        // Navegar para a tela de "Esqueci Minha Senha"
                    },
                    onSignUp = {
                        // Navegar para a tela de SignUp
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    NutriGlicoTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo da Plataforma",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 32.dp)
                    )

                    // Campo de E-mail
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Senha
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Senha") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                                    ),
                                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botão "Esqueci Minha Senha"
                    TextButton(
                        onClick = onForgotPassword,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Esqueci minha senha",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botão de Login
                    Button(
                        onClick = { onLogin(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "Login",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Texto e botão de SignUp
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Não possui uma conta?",
                            style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        ClickableText(
                            text = buildAnnotatedString { append("Cadastre-se") },
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            ),
                            onClick = { onSignUp() }
                        )
                    }
                }
            }
        }
    }
}
