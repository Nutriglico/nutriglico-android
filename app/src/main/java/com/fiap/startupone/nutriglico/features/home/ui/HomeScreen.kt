package com.fiap.startupone.nutriglico.features.home.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.commons.ui.CustomTopBar
import com.fiap.startupone.nutriglico.commons.ui.SectionTitle
import com.fiap.startupone.nutriglico.commons.ui.StandardCard
import com.fiap.startupone.nutriglico.features.glucose.register.ui.RegisterGlucoseActivity
import com.fiap.startupone.nutriglico.features.home.viewmodel.HomeAction
import com.fiap.startupone.nutriglico.features.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val homeState by viewModel.homeState.collectAsState()

    Scaffold(
        topBar = { CustomTopBar(title = "NutriGlico", onBack = null) },
        content = { padding ->
            HomeContent(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFF8F8F8)), // Cor gelo
                onCardClick = viewModel::onCardClick
            )
        }
    )

    when (homeState.action) {
        HomeAction.OpenRegisterGlucose -> {
            val context = LocalContext.current
            context.startActivity(Intent(context, RegisterGlucoseActivity::class.java))
            viewModel.resetAction()
        }
        else -> {}
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onCardClick: (HomeAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // 1. Minhas Medições
        SectionTitle(title = "Minhas Medições")
        StandardCard(
            icon = R.drawable.ic_glucose,
            title = "Glicemia",
            description = "120 mg/dL",
            rightIcon = R.drawable.ic_add,
            onClick = { onCardClick(HomeAction.OpenRegisterGlucose) }
        )
        StandardCard(
            icon = R.drawable.ic_monitor_weight,
            title = "Peso",
            description = "70 kg",
            rightIcon = R.drawable.ic_add,
            onClick = { /* Implementar ação */ }
        )
        StandardCard(
            icon = R.drawable.ic_vital_signs,
            title = "Pressão Arterial",
            description = "120/80 mmHg",
            rightIcon = R.drawable.ic_add,
            onClick = { /* Implementar ação */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Meus Registros
        SectionTitle(title = "Meus Registros")
        StandardCard(
            icon = R.drawable.ic_history,
            title = "Histórico de Registros",
            description = "1 registro",
            rightIcon = R.drawable.ic_chevron_right,
            onClick = { onCardClick(HomeAction.OpenHistory) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Minhas Refeições
        SectionTitle(title = "Minhas Refeições")
        StandardCard(
            icon = R.drawable.ic_food,
            title = "Refeição",
            description = "Sem Registro",
            rightIcon = R.drawable.ic_add,
            onClick = { onCardClick(HomeAction.OpenFood) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Minhas Medicações
        SectionTitle(title = "Minhas Medicações")
        StandardCard(
            icon = R.drawable.ic_pill,
            title = "Medicamentos",
            description = "Sem Registro",
            rightIcon = R.drawable.ic_add,
            onClick = { onCardClick(HomeAction.OpenMedication) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockViewModel = HomeViewModel()
    HomeScreen(
        viewModel = mockViewModel
    )
}