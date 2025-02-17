package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.commons.ui.CustomButton
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.EditProfileUIState
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.EditProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Editar Perfil", navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
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
                    value = cpf.value,
                    onValueChange = { cpf.value = it },
                    label = { Text("CPF") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                CustomButton(
                    text = "Salvar",
                    onClick = {
                        viewModel.updateUser(
                            name = name.value,
                            email = email.value,
                            cpf = cpf.value,
                            onSuccess = {
                                navController.popBackStack()
                            }
                        )
                    }
                )

                when (uiState) {
                    EditProfileUIState.Idle -> {
                        Text(
                            text = "Edite as informações e clique em salvar.",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    EditProfileUIState.Loading -> {
                        Text(
                            text = "Salvando...",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    EditProfileUIState.Success -> {
                        Text(
                            text = "Dados salvos com sucesso!",
                            fontSize = 16.sp,
                            color = Color.Green,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                        viewModel.resetState()
                        navController.popBackStack()
                    }

                    is EditProfileUIState.Error -> {
                        Text(
                            text = (uiState as EditProfileUIState.Error).message,
                            fontSize = 16.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }
    )
}
