package com.fiap.startupone.nutriglico.features.usermanagement.signup.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar

@Composable
fun SelectProfileScreen(
    navController: NavHostController,
    onPatientClick: () -> Unit,
    onNutritionistClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "Selecione seu Perfil", navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onPatientClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Text(text = "Paciente", fontSize = 18.sp)
                }

                Button(
                    onClick = onNutritionistClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Text(text = "Nutricionista", fontSize = 18.sp)
                }
            }
        }
    )
}
