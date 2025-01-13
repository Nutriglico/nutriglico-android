package com.fiap.startupone.nutriglico.commons.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.fiap.startupone.nutriglico.R

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