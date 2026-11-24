package com.example.di_iniciosesion_kmp.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import di_iniciosesion_kmp.shared.generated.resources.Res
import di_iniciosesion_kmp.shared.generated.resources.inter_bold
import di_iniciosesion_kmp.shared.generated.resources.inter_regular
import di_iniciosesion_kmp.shared.generated.resources.inter_semibold
import org.jetbrains.compose.resources.Font

// Tipografía Inter, la misma que se usa en el diseño de Figma.
// Los archivos están en composeResources/font (licencia SIL OFL).
// En Compose Multiplatform las fuentes se cargan dentro de funciones @Composable.
val Inter: FontFamily
    @Composable get() = FontFamily(
        Font(Res.font.inter_regular, FontWeight.Normal),
        Font(Res.font.inter_semibold, FontWeight.SemiBold),
        Font(Res.font.inter_bold, FontWeight.Bold)
    )

// Estilos de texto definidos en Figma (página "01 - Estilos")

// Título principal: Inter Bold 32
val TituloPrincipal: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )

// Subtítulo: Inter Semi Bold 20 (texto de los botones)
val Subtitulo: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )

// Texto normal: Inter Regular 16
val TextoNormal: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

// Texto auxiliar: Inter Regular 14
val TextoAuxiliar: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )

// Tipografía de Material con Inter como fuente por defecto
@Composable
fun tipografiaInter(): Typography {
    val base = Typography()
    val inter = Inter

    return Typography(
        displayLarge = base.displayLarge.copy(fontFamily = inter),
        displayMedium = base.displayMedium.copy(fontFamily = inter),
        displaySmall = base.displaySmall.copy(fontFamily = inter),
        headlineLarge = base.headlineLarge.copy(fontFamily = inter),
        headlineMedium = base.headlineMedium.copy(fontFamily = inter),
        headlineSmall = base.headlineSmall.copy(fontFamily = inter),
        titleLarge = base.titleLarge.copy(fontFamily = inter),
        titleMedium = base.titleMedium.copy(fontFamily = inter),
        titleSmall = base.titleSmall.copy(fontFamily = inter),
        bodyLarge = base.bodyLarge.copy(fontFamily = inter),
        bodyMedium = base.bodyMedium.copy(fontFamily = inter),
        bodySmall = base.bodySmall.copy(fontFamily = inter),
        labelLarge = base.labelLarge.copy(fontFamily = inter),
        labelMedium = base.labelMedium.copy(fontFamily = inter),
        labelSmall = base.labelSmall.copy(fontFamily = inter)
    )
}
