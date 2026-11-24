package com.example.di_iniciosesion_kmp.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val FigmaColorScheme = lightColorScheme(
    primary = Primario,
    secondary = Secundario,
    background = Fondo,
    surface = Blanco,
    onPrimary = Blanco,
    onSecondary = Blanco,
    onBackground = TextoPrincipal,
    onSurface = TextoPrincipal,
    error = ColorError
)

@Composable
fun DI_FigmaLoginTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FigmaColorScheme,
        typography = tipografiaInter(),
        content = content
    )
}
