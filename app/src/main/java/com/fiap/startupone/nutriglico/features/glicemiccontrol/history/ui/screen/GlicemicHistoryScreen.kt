package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.domain.usecase.FetchGlicemicHistoryUseCase
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.repository.GlicemicHistoryRepository
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel.GlicemicHistoryViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.koin.androidx.compose.koinViewModel

@Composable
fun GlicemicHistoryScreen(
    viewModel: GlicemicHistoryViewModel = koinViewModel(),
    navController: NavController,
    onItemClick: (String) -> Unit
) {
    val historyState by viewModel.historyState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Histórico de Glicemia", navController = navController)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Log.d("GlicemicHistoryScreen", "HistoryState: ${historyState.toString()}")
                when (historyState) {
                    is GlicemicHistoryViewModel.HistoryState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    is GlicemicHistoryViewModel.HistoryState.Success -> {
                        val history = (historyState as GlicemicHistoryViewModel.HistoryState.Success).history
                        GlicemicGraph(history)
                        Spacer(modifier = Modifier.height(16.dp))
                        GlicemicRecordList(history, onItemClick)
                    }

                    is GlicemicHistoryViewModel.HistoryState.Error -> {
                        Text(
                            text = (historyState as GlicemicHistoryViewModel.HistoryState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                    else -> {}
                }
            }
        }
    )
}

@Composable
fun GlicemicGraph(history: List<GlicemicHistoryResponse>) {
    val entries = history.mapIndexed { index, record ->
        Entry(index.toFloat(), record.level.toFloat())
    }

    val dataSet = LineDataSet(entries, "").apply { // Sem título na linha
        color = Color.Blue.toArgb()
        lineWidth = 2f
        setCircleColor(Color.Red.toArgb())
        circleRadius = 4f
        setDrawCircleHole(false)
        valueTextSize = 10f
        setDrawValues(false)
        setDrawFilled(true)
        fillColor = Color.Cyan.toArgb()
        fillAlpha = 50
    }

    val lineData = LineData(dataSet)

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                data = lineData
                description.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(false)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                setBackgroundColor(Color.White.toArgb())

                // Configurações do eixo X (Datas)
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    textColor = Color.Black.toArgb()
                    valueFormatter = IndexAxisValueFormatter(
                        history.map { it.registerDate.substring(0, 10) }
                    )
                    labelRotationAngle = -30f
                    granularity = 1f
                    isGranularityEnabled = true
                }

                // Configurações do eixo Y (Glicemia)
                axisLeft.apply {
                    axisMinimum = 20f // Configura o início do eixo Y no valor 50
                    setDrawGridLines(false)
                    textColor = Color.Black.toArgb()

                    // Espaçamento entre valores no eixo Y
                    labelCount = 5 // Define o número de labels no eixo Y
                    granularity = 20f // Define o espaçamento mínimo entre labels

                    // Adiciona a linha limite vermelha no valor 180 (máximo)
                    addLimitLine(
                        LimitLine(180f, "Máximo").apply {
                            lineColor = Color.Red.toArgb()
                            lineWidth = 2f
                            textColor = Color.Red.toArgb()
                            textSize = 12f
                            labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
                        }
                    )

                    // Adiciona a linha limite vermelha no valor 70 (mínimo)
                    addLimitLine(
                        LimitLine(70f, "Mínimo").apply {
                            lineColor = Color.Red.toArgb()
                            lineWidth = 2f
                            textColor = Color.Red.toArgb()
                            textSize = 12f
                            labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
                        }
                    )
                }

                // Desativa o eixo da direita
                axisRight.isEnabled = false

                // Configurações da legenda
                legend.isEnabled = false // Desativa completamente a legenda
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Diminui a altura do gráfico
    )
}

@Composable
fun GlicemicRecordList(
    history: List<GlicemicHistoryResponse>,
    onItemClick: (String) -> Unit
) {
    if (history.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Nenhum registro encontrado",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(history) { record ->
                GlicemicRecordListItem(record = record, onClick = { onItemClick(record.id) })
            }
        }
    }
}

@Composable
fun GlicemicRecordListItem(
    record: GlicemicHistoryResponse,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Data: ${record.registerDate}")
                Text(text = "Valor: ${record.level} mg/dL")
                Text(text = "Status: ${record.rate}")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GlicemicHistoryScreenPreview() {
    val navController = rememberNavController()
    GlicemicHistoryScreen(
        viewModel = GlicemicHistoryViewModel(
            FetchGlicemicHistoryUseCase(
                repository = object : GlicemicHistoryRepository {

                    override suspend fun fetchHistory(): List<GlicemicHistoryResponse> {
                        return listOf()
                    }

                    override suspend fun fetchDetails(id: String): GlicemicHistoryResponse {
                        return GlicemicHistoryResponse(
                            id = "",
                            level = 0,
                            registerDate = "",
                            type = "",
                            rate = "",
                            colorRate = ""
                        )
                    }

                    override suspend fun updateRecord(record: GlicemicHistoryResponse) {

                    }

                    override suspend fun deleteRecord(id: String) {

                    }
                }
            )
        ),
        navController = navController,
        onItemClick = {}
    )
}
