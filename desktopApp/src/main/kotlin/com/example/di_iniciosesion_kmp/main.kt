package com.example.di_iniciosesion_kmp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

// Punto de entrada de escritorio: abre una ventana con el tamaño aproximado
// de los frames de Figma (393 × 852) y muestra la App común de shared.
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DI - Inicio de sesión",
        state = rememberWindowState(width = 410.dp, height = 900.dp)
    ) {
        App()
    }
}
