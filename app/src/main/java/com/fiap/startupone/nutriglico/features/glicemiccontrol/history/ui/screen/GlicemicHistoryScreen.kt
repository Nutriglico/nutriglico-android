package com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.components.GlicemicGraph
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.components.GlicemicRecordList
import com.fiap.startupone.nutriglico.features.glicemiccontrol.history.ui.viewmodel.GlicemicHistoryViewModel
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
                Log.d("GlicemicHistoryScreen", "HistoryState: $historyState")
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
