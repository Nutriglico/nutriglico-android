package com.fiap.startupone.nutriglico.features.home.ui

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import com.fiap.startupone.nutriglico.features.glucose.register.ui.RegisterGlucoseActivity

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
    val context = LocalContext.current

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
            onClick = {
                val intent = Intent(context, RegisterGlucoseActivity::class.java)
                context.startActivity(intent)
            }
        )
        StandardCard(
            icon = R.drawable.ic_monitor_weight,
            title = "Peso",
            description = "70 kg",
            rightIcon = R.drawable.ic_add,
            onClick = { /* Ação ao clicar no card */ }
        )
        StandardCard(
            icon = R.drawable.ic_vital_signs,
            title = "Pressão Arterial",
            description = "120/80 mmHg",
            rightIcon = R.drawable.ic_add,
            onClick = { /* Ação ao clicar no card */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Meus Registros
        SectionTitle(title = "Meus Registros")
        StandardCard(
            icon = R.drawable.ic_history,
            title = "Histórico de Registros",
            description = "1 registro",
            rightIcon = R.drawable.ic_chevron_right,
            onClick = { /* Ação ao clicar no card */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Minhas Refeições
        SectionTitle(title = "Minhas Refeições")
        StandardCard(
            icon = R.drawable.ic_food,
            title = "Refeição",
            description = "Sem Registro",
            rightIcon = R.drawable.ic_add,
            onClick = { /* Ação ao clicar no card */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Minhas Medicações
        SectionTitle(title = "Minhas Medicações")
        StandardCard(
            icon = R.drawable.ic_pill,
            title = "Medicamentos",
            description = "Sem Registro",
            rightIcon = R.drawable.ic_add,
            onClick = { /* Ação ao clicar no card */ }
        )
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
fun StandardCard(
    icon: Int,
    title: String,
    description: String?,
    rightIcon: Int,
    onClick: () -> Unit
) {
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
                modifier = Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = description ?: "Sem Registro",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            IconButton(onClick = onClick) {
                Icon(
                    painter = painterResource(id = rightIcon),
                    contentDescription = null
                )
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
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_food),
                    contentDescription = "Refeição"
                )
            },
            label = { Text("Refeição") },
            selected = false,
            onClick = { /* Navigate */ }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_glucose),
                    contentDescription = "Glicemia"
                )
            },
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
