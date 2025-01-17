package com.fiap.startupone.nutriglico.features.usermanagement.signup.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.usermanagement.signup.data.model.UserRequest
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.UserManagementUIState
import com.fiap.startupone.nutriglico.features.usermanagement.signup.viewmodel.UserManagementViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterPatientScreen(
    navController: NavHostController,
    onRegisterSuccess: () -> Unit,
    viewModel: UserManagementViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val birthDate = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Cadastro do Paciente", navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Nome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Senha") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = birthDate.value,
                    onValueChange = { birthDate.value = it },
                    label = { Text("Data de Nascimento") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        viewModel.registerPatient(
                            userRequest = UserRequest(
                                name = name.value,
                                email = email.value,
                                password = password.value,
                                birthDate = birthDate.value
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(text = "Cadastrar", fontSize = 18.sp)
                }

                when (uiState) {
                    is UserManagementUIState.Loading -> Text("Carregando...", modifier = Modifier.padding(top = 16.dp))
                    is UserManagementUIState.Success -> {
                        Text("Cadastro realizado com sucesso!", modifier = Modifier.padding(top = 16.dp))
                        onRegisterSuccess()
                    }
                    is UserManagementUIState.Error -> Text(
                        text = (uiState as UserManagementUIState.Error).message,
                        modifier = Modifier.padding(top = 16.dp),
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                    is UserManagementUIState.ValidationError -> Text(
                        text = (uiState as UserManagementUIState.ValidationError).message,
                        modifier = Modifier.padding(top = 16.dp),
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                    else -> {}
                }
            }
        }
    )
}
