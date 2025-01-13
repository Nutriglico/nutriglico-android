package com.fiap.startupone.nutriglico.features.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fiap.startupone.nutriglico.R
import com.fiap.startupone.nutriglico.features.home.viewmodel.HomeViewModel
import com.fiap.startupone.nutriglico.ui.theme.NutriGlicoTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriGlicoTheme {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "NutriGlico",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        },
        content = { padding ->
            HomeContent(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color(0xFFF8F8F8)) // Cor gelo
            )
        }
    )
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        SectionTitle(title = "Minhas Refeições")
        SimpleCard(
            title = "Refeição",
            subtitle = "Sem Registro",
            action = "Adicionar agora :)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle(title = "Minhas Medicações")
        SimpleCard(
            title = "Medicamentos",
            subtitle = "Sem Registro",
            action = "Adicionar agora :)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle(title = "Minhas Medições")
        MeasurementCard(icon = R.drawable.ic_glucose, title = "Glicemia", value = "Sem Registro")
        MeasurementCard(icon = R.drawable.ic_monitor_weight, title = "Peso", value = "Sem Registro")
        MeasurementCard(icon = R.drawable.ic_vital_signs, title = "Pressão Arterial", value = "Sem Registro")

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle(title = "Meus Registros")
        SimpleCard(title = "Histórico de Registros", subtitle = "1 registro", action = "Ver mais")
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun MeasurementCard(icon: Int, title: String, value: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Fundo branco
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = value, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Action */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Adicionar"
                )
            }
        }
    }
}

@Composable
fun SimpleCard(title: String, subtitle: String, action: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Fundo branco
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = action, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFFF8F8F8) // Cor gelo
    ) {
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_food), contentDescription = "Refeição") },
            label = { Text("Refeição") },
            selected = false,
            onClick = { /* Navigate */ }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_glucose), contentDescription = "Glicemia") },
            label = { Text("Glicemia") },
            selected = true,
            onClick = { /* Navigate */ }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_menu), contentDescription = "Menu") },
            label = { Text("Menu") },
            selected = false,
            onClick = { /* Navigate */ }
        )
    }
}
