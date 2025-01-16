package com.fiap.startupone.nutriglico.features.usermanagement.signup.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.UserSignUpViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.SignupUiState

@Composable
fun UserSignUpScreen(
    viewModel: UserSignUpViewModel,
    onBack: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is SignupUiState.Success) {
            onSignUpSuccess()
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Cadastro", onBack = onBack)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Button(
                    onClick = { viewModel.registerUser(name, email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar")
                }

                when (uiState) {
                    is SignupUiState.Loading -> CircularProgressIndicator()
                    is SignupUiState.Success -> Text((uiState as SignupUiState.Success).message)
                    is SignupUiState.Error -> Text((uiState as SignupUiState.Error).errorMessage, color = MaterialTheme.colorScheme.error)
                    else -> {}
                }
            }
        }
    )
}
