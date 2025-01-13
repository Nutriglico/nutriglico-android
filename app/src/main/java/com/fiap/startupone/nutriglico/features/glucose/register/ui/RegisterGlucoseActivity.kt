package com.fiap.startupone.nutriglico.features.glucose.register.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.features.glucose.register.viewmodel.RegisterGlucoseViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class RegisterGlucoseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                val viewModel: RegisterGlucoseViewModel = koinViewModel()
                RegisterGlucoseScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterGlucoseScreen(viewModel: RegisterGlucoseViewModel, onBack: () -> Unit) {
    val successMessage by viewModel.successMessage.collectAsState()

    val context = LocalContext.current

    // Estados iniciais para data e hora
    var date by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) }
    var time by remember { mutableStateOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))) }

    // Scaffold para a tela
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Glicemia") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
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
                var glucoseValue by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = glucoseValue,
                    onValueChange = { glucoseValue = it },
                    label = { Text("Valor da Glicemia (mg/dL)") },
                    placeholder = { Text("Ex.: 120") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Botão de Salvar
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val value = glucoseValue.toIntOrNull() ?: 0
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

fun showDatePickerDialog(context: Context, onDateChange: (String) -> Unit) {
    val initialYear = LocalDate.now().year
    val initialMonth = LocalDate.now().monthValue - 1
    val initialDay = LocalDate.now().dayOfMonth

    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = LocalDate.of(year, month + 1, dayOfMonth)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            onDateChange(formattedDate)
        },
        initialYear,
        initialMonth,
        initialDay
    ).show()
}

fun showTimePickerDialog(context: Context, onTimeChange: (String) -> Unit) {
    val initialHour = LocalTime.now().hour
    val initialMinute = LocalTime.now().minute

    android.app.TimePickerDialog(
        context,
        { _, hour, minute ->
            val formattedTime = LocalTime.of(hour, minute)
                .format(DateTimeFormatter.ofPattern("HH:mm"))
            onTimeChange(formattedTime)
        },
        initialHour,
        initialMinute,
        true // formato 24 horas
    ).show()
}
