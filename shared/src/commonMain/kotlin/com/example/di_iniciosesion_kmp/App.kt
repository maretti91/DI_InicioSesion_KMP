package com.example.di_iniciosesion_kmp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.di_iniciosesion_kmp.theme.*
import di_iniciosesion_kmp.shared.generated.resources.Res
import di_iniciosesion_kmp.shared.generated.resources.ic_chevron_down
import di_iniciosesion_kmp.shared.generated.resources.ic_chevron_left
import di_iniciosesion_kmp.shared.generated.resources.ic_google
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

// PUNTO DE ENTRADA COMÚN
// Lo llaman androidApp (MainActivity) y desktopApp (main.kt).
// Todo el código de las pantallas está aquí, en commonMain, y se comparte entre plataformas.

@Composable
fun App() {
    DI_FigmaLoginTheme {
        Aplicacion()
    }
}

// MEDIDAS TOMADAS DEL DISEÑO DE FIGMA (frame de 393 × 852)

// Margen lateral: (393 - 329) / 2 = 32 dp, así los campos miden 329 dp de ancho
private val MargenLateral = 32.dp

// Altura de campos y botones
private val AlturaElemento = 52.dp

// Radio de las esquinas de campos y botones
private val RadioEsquinas = 8.dp

// Separación entre la etiqueta y su campo
private val EspacioEtiqueta = 8.dp

// NAVEGACIÓN ENTRE PANTALLAS Y MENSAJES
// Reproduce las interacciones del prototipo de Figma:
// "Regístrate ahora" -> Registro, "Inicia sesión" y Chevron left -> Login.
// Los Toast solo existen en Android, así que los mensajes se muestran
// con un Snackbar de Material 3, que funciona en todas las plataformas.

@Composable
fun Aplicacion() {
    var pantalla by rememberSaveable {
        mutableStateOf("login")
    }

    val estadoMensajes = remember { SnackbarHostState() }
    val ambito = rememberCoroutineScope()

    val mostrarMensaje: (String) -> Unit = { texto ->
        ambito.launch {
            estadoMensajes.currentSnackbarData?.dismiss()
            estadoMensajes.showSnackbar(texto)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (pantalla) {
            "login" -> PantallaLogin(
                irRegistro = { pantalla = "registro" },
                mostrarMensaje = mostrarMensaje
            )

            "registro" -> PantallaRegistro(
                volverLogin = { pantalla = "login" },
                mostrarMensaje = mostrarMensaje
            )
        }

        SnackbarHost(
            hostState = estadoMensajes,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
        )
    }
}

// PANTALLA DE INICIO DE SESIÓN (frame "Login - Inicio sesión")

@Composable
fun PantallaLogin(
    irRegistro: () -> Unit,
    mostrarMensaje: (String) -> Unit
) {
    var correo by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    ContenedorPantalla {

        // En Login no hay icono de volver, pero se reserva su espacio
        // para que el título quede a la misma altura que en Registro (y = 81)
        Spacer(modifier = Modifier.height(18.dp + 24.dp))

        Titulo(texto = "Iniciar sesión")

        Spacer(modifier = Modifier.height(23.dp))

        CampoTexto(
            etiqueta = "Correo electrónico",
            placeholder = "ejemplo@correo.com",
            valor = correo,
            alCambiar = { correo = it },
            tipoTeclado = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            placeholder = "********",
            valor = password,
            alCambiar = { password = it },
            esPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotonPrincipal(
            texto = "Iniciar Sesión",
            alPulsar = {
                if (correo.isBlank() || password.isBlank()) {
                    mostrarMensaje("Completa todos los campos")
                } else {
                    mostrarMensaje("Inicio de sesión de demostración")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            style = TextoAuxiliar,
            color = Negro,
            modifier = Modifier.clickable {
                mostrarMensaje("Recuperación no implementada")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BotonGoogle(
            alPulsar = {
                mostrarMensaje("Acceso con Google no implementado")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        EnlaceTexto(
            texto = "¿No tienes cuenta? ",
            enlace = "Regístrate ahora",
            alPulsar = irRegistro
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// PANTALLA DE REGISTRO (frame "Registro - Crear cuenta")

@Composable
fun PantallaRegistro(
    volverLogin: () -> Unit,
    mostrarMensaje: (String) -> Unit
) {
    var nombre by rememberSaveable {
        mutableStateOf("")
    }

    var correo by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var repetirPassword by rememberSaveable {
        mutableStateOf("")
    }

    var pais by rememberSaveable {
        mutableStateOf("")
    }

    ContenedorPantalla {

        Spacer(modifier = Modifier.height(18.dp))

        // Icono Chevron left: acción "Back" del prototipo.
        // En Figma está a 21 px del borde izquierdo (11 px menos que el margen).
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(Res.drawable.ic_chevron_left),
                contentDescription = "Volver",
                modifier = Modifier
                    .offset(x = (-11).dp)
                    .size(24.dp)
                    .clickable { volverLogin() }
            )
        }

        Titulo(texto = "Crear cuenta")

        Spacer(modifier = Modifier.height(23.dp))

        CampoTexto(
            etiqueta = "Nombre y apellidos",
            placeholder = "Nombre completo",
            valor = nombre,
            alCambiar = { nombre = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoTexto(
            etiqueta = "Correo electrónico",
            placeholder = "ejemplo@correo.com",
            valor = correo,
            alCambiar = { correo = it },
            tipoTeclado = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            placeholder = "********",
            valor = password,
            alCambiar = { password = it },
            esPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        CampoTexto(
            etiqueta = "Repetir contraseña",
            placeholder = "********",
            valor = repetirPassword,
            alCambiar = { repetirPassword = it },
            esPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        SelectorPais(
            paisSeleccionado = pais,
            alSeleccionar = { pais = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        BotonPrincipal(
            texto = "Registrarse",
            alPulsar = {
                when {
                    nombre.isBlank() ||
                            correo.isBlank() ||
                            password.isBlank() ||
                            repetirPassword.isBlank() ||
                            pais.isBlank() -> {
                        mostrarMensaje("Completa todos los campos")
                    }

                    password != repetirPassword -> {
                        mostrarMensaje("Las contraseñas no coinciden")
                    }

                    else -> {
                        mostrarMensaje("Registro de demostración completado")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        EnlaceTexto(
            texto = "¿Ya tienes cuenta? ",
            enlace = "Inicia sesión",
            alPulsar = volverLogin
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// COMPONENTE: CONTENEDOR COMÚN DE LAS PANTALLAS
// Fondo (#EAF2F8), márgenes laterales y desplazamiento vertical

@Composable
fun ContenedorPantalla(
    contenido: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MargenLateral),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = contenido
    )
}

// COMPONENTE: TÍTULO DE PANTALLA (estilo "Titulo Principal")

@Composable
fun Titulo(texto: String) {
    Spacer(modifier = Modifier.height(39.dp))

    Text(
        text = texto,
        style = TituloPrincipal,
        color = Negro,
        textAlign = TextAlign.Center
    )
}

// COMPONENTE REUTILIZABLE: CAMPO DE TEXTO (componente "Input - Base")

@Composable
fun CampoTexto(
    etiqueta: String,
    placeholder: String,
    valor: String,
    alCambiar: (String) -> Unit,
    esPassword: Boolean = false,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    val estiloPlaceholder = TextoNormal

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Etiqueta(texto = etiqueta)

        BasicTextField(
            value = valor,
            onValueChange = alCambiar,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esPassword) {
                    KeyboardType.Password
                } else {
                    tipoTeclado
                }
            ),
            visualTransformation = if (esPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            textStyle = TextoNormal.copy(
                color = TextoPrincipal,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AlturaElemento)
                        .background(
                            Blanco,
                            RoundedCornerShape(RadioEsquinas)
                        )
                        .border(
                            1.dp,
                            Borde,
                            RoundedCornerShape(RadioEsquinas)
                        )
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (valor.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = estiloPlaceholder,
                            color = TextoSecundario,
                            textAlign = TextAlign.Center
                        )
                    }

                    innerTextField()
                }
            }
        )
    }
}

// COMPONENTE: ETIQUETA DE CAMPO (estilo "Texto normal", centrada)

@Composable
fun Etiqueta(texto: String) {
    Text(
        text = texto,
        style = TextoNormal,
        color = Negro,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = EspacioEtiqueta),
        textAlign = TextAlign.Center
    )
}

// COMPONENTE REUTILIZABLE: BOTÓN PRINCIPAL (componente "Boton Primario")

@Composable
fun BotonPrincipal(
    texto: String,
    alPulsar: () -> Unit
) {
    Button(
        onClick = alPulsar,
        modifier = Modifier
            .fillMaxWidth()
            .height(AlturaElemento),
        shape = RoundedCornerShape(RadioEsquinas),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primario,
            contentColor = Blanco
        ),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Text(
            text = texto,
            style = Subtitulo
        )
    }
}

// COMPONENTE: BOTÓN DE ACCESO CON GOOGLE
// Fondo blanco sin borde, icono a 44 px del borde y texto a 26 px del icono

@Composable
fun BotonGoogle(
    alPulsar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(AlturaElemento)
            .background(
                Blanco,
                RoundedCornerShape(RadioEsquinas)
            )
            .clickable { alPulsar() }
            .padding(start = 44.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_google),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(26.dp))

        Text(
            text = "Iniciar sesión con Google",
            style = TextoNormal,
            color = TextoPrincipal
        )
    }
}

// COMPONENTE: TEXTO CON ENLACE
// Primera parte en color de texto principal y enlace en color secundario (#36B37E)

@Composable
fun EnlaceTexto(
    texto: String,
    enlace: String,
    alPulsar: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            style = TextoAuxiliar,
            color = TextoPrincipal
        )

        Text(
            text = enlace,
            style = TextoAuxiliar,
            color = Secundario,
            modifier = Modifier.clickable { alPulsar() }
        )
    }
}

// COMPONENTE: SELECTOR DE PAÍS (campo "Input - Selecciona tu País" + Chevron down)

@Composable
fun SelectorPais(
    paisSeleccionado: String,
    alSeleccionar: (String) -> Unit
) {
    var expandido by remember {
        mutableStateOf(false)
    }

    val paises = listOf(
        "España",
        "Italia",
        "Francia",
        "Alemania",
        "Portugal",
        "Argentina",
        "México",
        "Colombia",
        "Otro"
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Etiqueta(texto = "País")

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AlturaElemento)
                    .background(
                        Blanco,
                        RoundedCornerShape(RadioEsquinas)
                    )
                    .border(
                        1.dp,
                        Borde,
                        RoundedCornerShape(RadioEsquinas)
                    )
                    .clickable {
                        expandido = true
                    }
            ) {
                // Texto centrado en el campo, como en Figma
                Text(
                    text = paisSeleccionado.ifEmpty {
                        "Selecciona tu País"
                    },
                    style = TextoNormal,
                    color = if (paisSeleccionado.isEmpty()) {
                        TextoSecundario
                    } else {
                        TextoPrincipal
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Chevron down a 28 px del borde derecho
                Image(
                    painter = painterResource(Res.drawable.ic_chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 28.dp)
                        .size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expandido,
                onDismissRequest = {
                    expandido = false
                }
            ) {
                paises.forEach { pais ->
                    DropdownMenuItem(
                        text = {
                            Text(pais, style = TextoNormal)
                        },
                        onClick = {
                            alSeleccionar(pais)
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}

// VISTAS PREVIAS DE LAS DOS PANTALLAS

@Preview
@Composable
fun VistaPreviaLogin() {
    DI_FigmaLoginTheme {
        PantallaLogin(irRegistro = {}, mostrarMensaje = {})
    }
}

@Preview
@Composable
fun VistaPreviaRegistro() {
    DI_FigmaLoginTheme {
        PantallaRegistro(volverLogin = {}, mostrarMensaje = {})
    }
}
