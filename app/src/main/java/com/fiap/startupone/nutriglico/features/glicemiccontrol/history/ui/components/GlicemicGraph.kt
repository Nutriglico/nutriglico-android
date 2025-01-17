package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.data.model.GlicemicHistoryResponse
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

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