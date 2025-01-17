package com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.viewmodel.UserAuthUiState
import com.fiap.startupone.nutriglico.features.usermanagement.auth.ui.viewmodel.UserAuthViewModel

@Composable
fun UserAuthScreen(
    viewModel: UserAuthViewModel,
    onUserAuthSuccess: () -> Unit,
    onSignupClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("test@example.com") }
    var password by remember { mutableStateOf("password123") }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Imagem do Logo
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Logo da Plataforma",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(120.dp)
                        .padding(bottom = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo de Senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                // Botão de Esqueci Minha Senha
                TextButton(
                    onClick = { onForgotPasswordClick() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Esqueci minha senha")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão de Login
                Button(
                    onClick = { viewModel.authenticate(email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entrar")
                }

                // Botão de SignUp
                TextButton(
                    onClick = onSignupClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Cadastrar-se")
                }

                // Gerenciamento de Estados
                when (uiState) {
                    is UserAuthUiState.Loading -> CircularProgressIndicator()
                    is UserAuthUiState.Success -> onUserAuthSuccess()
                    is UserAuthUiState.Error -> Text(
                        (uiState as UserAuthUiState.Error).errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )

                    else -> {}
                }
            }
        }
    )
}
