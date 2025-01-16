package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel.GlicemicRecordDetailViewModel

@Composable
fun GlicemicRecordDetailScreen(
    viewModel: GlicemicRecordDetailViewModel = viewModel(),
    recordId: String,
    navController: NavController
) {
    val recordState by viewModel.recordState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(title = "Editar Registro", navController = navController)
        },
        content = { padding ->
            when (recordState) {
                is GlicemicRecordDetailViewModel.RecordState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is GlicemicRecordDetailViewModel.RecordState.Success -> {
                    val record = (recordState as GlicemicRecordDetailViewModel.RecordState.Success).record
                    var type by remember { mutableStateOf(record.type) }
                    var level by remember { mutableStateOf(record.level.toString()) }
                    var registerDate by remember { mutableStateOf(record.registerDate) }
                    var rate by remember { mutableStateOf(record.rate) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = type,
                            onValueChange = { type = it },
                            label = { Text("Tipo de Medição") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = level,
                            onValueChange = { level = it.filter { char -> char.isDigit() } },
                            label = { Text("Nível de Glicemia (mg/dL)") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = registerDate,
                            onValueChange = { registerDate = it },
                            label = { Text("Data do Registro") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = rate,
                            onValueChange = { rate = it },
                            label = { Text("Status") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    val glicemicHistoryResponse = GlicemicHistoryResponse(
                                        id = recordId,
                                        level = level.toInt(),
                                        registerDate = registerDate,
                                        type = type,
                                        rate = rate
                                    )
                                    viewModel.updateRecord(glicemicHistoryResponse)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Salvar")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = { viewModel.deleteRecord(recordId) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Excluir")
                            }
                        }
                    }
                }
                is GlicemicRecordDetailViewModel.RecordState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (recordState as GlicemicRecordDetailViewModel.RecordState.Error).message,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadRecord(recordId) }) {
                                Text("Tentar Novamente")
                            }
                        }
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Carregando informações do registro...")
                    }
                }
            }
        }
    )
}
