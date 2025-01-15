package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.commons.ui.showDatePickerDialog
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.RegisterGlicemicControlRepositoryImpl
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.GlicemicLevelResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.model.RegisterGlicemicLevelRequest
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.data.service.RegisterGlicemicControlService
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.domain.usecase.SaveGlicemicControlMeasurementUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel.RegisterGlicemicControlViewModel
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun RegisterGlicemicControlScreen(
    viewModel: RegisterGlicemicControlViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var date by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) }
    var glucoseLevel by remember { mutableStateOf("") }
    var measurementType by remember { mutableStateOf("FAST") }

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Jejum", "Aleatório")

    Scaffold(
        topBar = {
            CustomTopBar(title = "Registro de Glicemia", navController = navController)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Campo de Data
                OutlinedTextField(
                    value = date,
                    onValueChange = { },
                    label = { Text("Data") },
                    placeholder = { Text("Selecione uma data (dd/mm/aaaa)") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            showDatePickerDialog(context) { selectedDate ->
                                date = selectedDate
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_calendar),
                                contentDescription = "Selecionar Data"
                            )
                        }
                    },
                    readOnly = true
                )

                // Campo Dropdown para Tipo de Medição
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (measurementType == "FAST") "Jejum" else "Aleatório",
                        onValueChange = { },
                        label = { Text("Tipo de Medição") },
                        placeholder = { Text("Escolha o tipo") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                                    contentDescription = "Selecionar Tipo de Medição"
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    measurementType = if (option == "Jejum") "FAST" else "RANDOM"
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Campo de Valor da Glicemia
                OutlinedTextField(
                    value = glucoseLevel,
                    onValueChange = { glucoseLevel = it },
                    label = { Text("Valor da Glicemia (mg/dL)") },
                    placeholder = { Text("Insira o valor em mg/dL") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Botão de Salvar
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        viewModel.saveMeasurement(glucoseLevel, date, measurementType)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Salvar")
                }

                // Exibição de Estados
                when (uiState) {
                    is RegisterGlicemicControlViewModel.UiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is RegisterGlicemicControlViewModel.UiState.Success -> {
                        Text(
                            "Medição salva com sucesso!",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    is RegisterGlicemicControlViewModel.UiState.Error -> {
                        val error =
                            (uiState as RegisterGlicemicControlViewModel.UiState.Error).message
                        Text("Erro: $error", color = MaterialTheme.colorScheme.error)
                    }

                    else -> {}
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockService = object : RegisterGlicemicControlService {
        override suspend fun registerGlicemicLevel(request: RegisterGlicemicLevelRequest): Response<GlicemicLevelResponse> {
            return Response.success(GlicemicLevelResponse("", 0, ""))
        }

        override suspend fun getGlicemicHistory(): Response<List<GlicemicLevelResponse>> {
            return Response.success(emptyList())
        }
    }
    val mockRepository = RegisterGlicemicControlRepositoryImpl(service = mockService)
    val mockSaveMeasurementUseCase = SaveGlicemicControlMeasurementUseCase(repository = mockRepository)
    val mockViewModel = RegisterGlicemicControlViewModel(saveMeasurementUseCase = mockSaveMeasurementUseCase)
    val navController = rememberNavController()
    RegisterGlicemicControlScreen(
        viewModel = mockViewModel,
        navController = navController
    )
}