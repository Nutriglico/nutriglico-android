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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.viewmodel.GlicemicRecordDetailViewModel

@Composable
fun GlicemicRecordDetailScreen(
    viewModel: GlicemicRecordDetailViewModel = viewModel(),
    recordId: String,
    navController: NavController,
    onEdit: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    val recordState by viewModel.recordState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRecord(recordId)
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Detalhes do Registro", navController = navController)
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
                    val statusColor = when (record.colorRate) {
                        "GREEN" -> Color.Green
                        "YELLOW" -> Color.Yellow
                        "RED" -> Color.Red
                        else -> Color.Gray
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DetailItem(label = "Tipo de Medição", value = record.type)
                        DetailItem(label = "Nível de Glicemia", value = "${record.level} mg/dL")
                        DetailItem(label = "Data do Registro", value = record.registerDate)
                        DetailItem(label = "Status", value = record.rate, color = statusColor)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { onEdit(recordId) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = { onDelete(recordId) },
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

@Composable
fun DetailItem(label: String, value: String, color: Color = Color.Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = color)
    }
}
