package com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.commons.ui.CustomButton
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.ProfileUIState
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.ProfileViewModel
import com.fiap.startupone.nutriglico.features.usermanagement.profile.ui.viewmodel.ProfileViewModelContract
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModelContract = koinViewModel<ProfileViewModel>(),
    navController: NavController,
    userId: String,
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (uiState is ProfileUIState.Idle) {
            viewModel.loadUserDetails(userId)
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Perfil", navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (uiState) {
                    ProfileUIState.Idle -> CircularProgressIndicator()
                    ProfileUIState.Loading -> CircularProgressIndicator()
                    is ProfileUIState.Success -> {
                        val user = (uiState as ProfileUIState.Success).user
                        Text("Nome: ${user.name}", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
                        Text("Email: ${user.email}", fontSize = 18.sp, modifier = Modifier.padding(vertical = 8.dp))
                        CustomButton(
                            text = "Alterar Dados",
                            onClick = { navController.navigate("editProfile") }
                        )
                        CustomButton(
                            text = "Deletar Conta",
                            onClick = {
                                viewModel.deleteAccount(
                                    userId = user.id,
                                    onSuccess = { navigateToLogin() },
                                    onError = { errorMessage -> viewModel.showError(errorMessage) }
                                )
                            }
                        )
                    }
                    is ProfileUIState.Error -> {
                        Text(
                            text = (uiState as ProfileUIState.Error).message,
                            fontSize = 16.sp,
                            color = androidx.compose.ui.graphics.Color.Red,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }
    )
}
