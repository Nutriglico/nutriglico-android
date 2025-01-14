package com.fiap.startupone.nutriglico.features.glicemiccontrol.register.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.commons.ui.showDatePickerDialog
import com.fiap.startupone.nutriglico.commons.ui.showTimePickerDialog
import com.fiap.startupone.nutriglico.features.glicemiccontrol.register.viewmodel.RegisterGlicemicControlViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun RegisterGlicemicControlScreen(viewModel: RegisterGlicemicControlViewModel, navController: NavController) {
    val context = LocalContext.current
    val successMessage by viewModel.successMessage.collectAsState()

    // Estados iniciais para data e hora
    var date by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) }
    var time by remember { mutableStateOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))) }

    // Scaffold para a tela
    Scaffold(
        topBar = {
            CustomTopBar(title = "NutriGlico", navController = navController)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showDatePickerDialog(context) { selectedDate ->
                                date = selectedDate
                            }
                        },
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

                // Campo de Horário
                OutlinedTextField(
                    value = time,
                    onValueChange = { },
                    label = { Text("Horário") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showTimePickerDialog(context) { selectedTime ->
                                time = selectedTime
                            }
                        },
                    trailingIcon = {
                        IconButton(onClick = {
                            showTimePickerDialog(context) { selectedTime ->
                                time = selectedTime
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_more_time),
                                contentDescription = "Selecionar Horário"
                            )
                        }
                    },
                    readOnly = true
                )

                // Campo de Valor da Glicemia
                var glicemicControlValue by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = glicemicControlValue,
                    onValueChange = { glicemicControlValue = it },
                    label = { Text("Valor da Glicemia (mg/dL)") },
                    placeholder = { Text("Ex.: 120") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Botão de Salvar
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val value = glicemicControlValue.toIntOrNull() ?: 0
                        viewModel.saveMeasurement(date, time, value, "")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salvar Medição")
                }

                // Mensagem de Sucesso
                if (successMessage != null) {
                    Text(
                        text = successMessage ?: "Sucesso",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}
