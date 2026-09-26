# MusicaApp 🎵

Aplicación Android desarrollada en **Kotlin** con **Jetpack Compose** que consume una API REST de música, permitiendo explorar álbumes, ver su detalle y reproducir contenido desde un mini reproductor integrado.

## Características

- Exploración de álbumes desde una API REST externa
- Pantalla de detalle por álbum
- Reproductor de música integrado (MiniPlayer)
- Navegación entre pantallas con Jetpack Navigation
- Llamadas asíncronas a la API mediante Coroutines
- Arquitectura organizada por capas (data, remote, screens, components)

## Tecnologías utilizadas

- **Kotlin**
- **Jetpack Compose** (UI declarativa)
- **Coroutines** (manejo de asincronía)
- **Retrofit** (consumo de API REST)
- **Gradle Kotlin DSL** (`.kts`)

## Estructura del proyecto
app/
├── components/ # Componentes reutilizables (MiniPlayer, etc.)
├── data/ # Repositorios (MusicRepository)
├── models/ # Modelos de datos (Album, AlbumDetail)
├── navigation/ # Rutas y navegación entre pantallas
├── remote/ # Configuración de red (MusicApi, NetworkModule)
└── screens/ # Pantallas de la app (Home, Detail)


## Cómo ejecutarlo

1. Clona el repositorio: git clone https://github.com/Lobo-Solitariotoward/MusicaApp.git

2. Ábrelo en **Android Studio**.
3. Sincroniza Gradle.
4. Ejecuta la app en un emulador o dispositivo físico.

## Autor

Desarrollado por Max ([GitHub](https://github.com/Lobo-Solitariotoward))
