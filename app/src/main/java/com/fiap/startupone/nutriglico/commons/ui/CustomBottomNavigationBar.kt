package com.fiap.startupone.nutriglico.commons.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.fiap.startupone.nutriglico.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_glucose),
                    contentDescription = "Glicemia"
                )
            },
            label = { Text("Glicemia") },
            selected = false,
            onClick = { navController.navigate("glicemia") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "Inserir") },
            label = { Text("Inserir") },
            selected = false,
            onClick = { navController.navigate("inserir") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.ic_food),
                    contentDescription = "Refeição"
                )
            },
            label = { Text("Refeição") },
            selected = false,
            onClick = { navController.navigate("refeicao") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.ic_menu), contentDescription = "Menu") },
            label = { Text("Menu") },
            selected = false,
            onClick = { navController.navigate("menu") }
        )
    }
}