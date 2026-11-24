# DI – Inicio de sesión y registro (Figma → Kotlin Multiplatform)

**Alumno:** Marco Maretti · 2º DAM semipresencial
**Módulo:** Desarrollo de Interfaces

Aplicación en **Kotlin Multiplatform** con **Compose Multiplatform** que reproduce las dos pantallas diseñadas en Figma en la actividad anterior. Funciona en **Android** y en **escritorio** (Windows, macOS y Linux) con el mismo código de interfaz.

- **Login – Inicio sesión:** correo, contraseña, botón «Iniciar Sesión», «¿Olvidaste tu contraseña?», acceso con Google y enlace «Regístrate ahora».
- **Registro – Crear cuenta:** nombre y apellidos, correo, contraseña, repetir contraseña, selector de país, botón «Registrarse», enlace «Inicia sesión» y flecha para volver.

**Criterios:** RA1. Genera interfaces gráficos de usuario mediante editores visuales utilizando las funcionalidades del editor y adaptando el código generado. CE1.h) Se ha desarrollado una aplicación que incluye el interfaz gráfico obtenido.

## Cómo ejecutarla

1. Abrir el proyecto en **Android Studio** (con el plugin Kotlin Multiplatform) o en **IntelliJ IDEA** y esperar a que termine la sincronización de Gradle.
2. **Escritorio:** ejecutar la configuración `desktopApp`, o desde terminal `./gradlew :desktopApp:run` (en Windows `gradlew.bat :desktopApp:run`).
3. **Android:** ejecutar la configuración `androidApp` en un emulador o móvil (API 24 o superior).

## Estructura del proyecto

| Módulo | Contenido |
|---|---|
| `shared/src/commonMain/kotlin/.../App.kt` | Las dos pantallas y sus componentes (código común a todas las plataformas) |
| `shared/src/commonMain/kotlin/.../theme/` | Colores, tipografía Inter y tema, sacados de los estilos de Figma |
| `shared/src/commonMain/composeResources/` | Fuente Inter e iconos (Google, Chevron left, Chevron down) |
| `androidApp/` | Punto de entrada de Android (`MainActivity`) |
| `desktopApp/` | Punto de entrada de escritorio (`main.kt`), ventana de 410 × 900 |

## Del diseño de Figma al código

| Figma | Compose Multiplatform |
|---|---|
| Estilos de color (Primario, Secundario, Fondo…) | `theme/Color.kt` |
| Estilos de texto (Título principal, Subtítulo, Texto normal, Texto auxiliar) | `theme/Type.kt` |
| Componente «Input - Base» | `CampoTexto` |
| Componente «Boton Primario» | `BotonPrincipal` |
| Botón de Google con su icono | `BotonGoogle` |
| Campo País + icono Chevron down | `SelectorPais` |
| Icono Chevron left (acción *Back*) | Flecha de la pantalla de registro |
| Interacciones del prototipo (*Navigate to* / *Back*) | `Aplicacion()` cambia entre las dos pantallas |

Las medidas (campos y botones de 329 × 52, esquinas de 8, bordes de 1 y separaciones) se han tomado de las propiedades de cada elemento en Figma. Las posiciones absolutas del diseño se han pasado a un `Column` con separadores, para que la interfaz se adapte a distintos tamaños de pantalla.

**Funcionalidad añadida respecto al prototipo:** los campos son editables, las contraseñas se ocultan, el selector de país despliega una lista y los botones validan los campos con mensajes (Snackbar, que funciona en todas las plataformas). El inicio de sesión, el registro, la recuperación de contraseña y el acceso con Google son solo de demostración.

## Créditos

Fuente [Inter](https://rsms.me/inter/) con licencia SIL Open Font License 1.1.
