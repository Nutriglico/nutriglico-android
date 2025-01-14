package com.fiap.startupone.nutriglico.commons.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExitAppDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmação") },
        text = { Text("Deseja sair do App?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Sim")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Não")
            }
        }
    )
}