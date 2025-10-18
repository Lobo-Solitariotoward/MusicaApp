package com.example.musicaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.musicaapp.components.MiniPlayer
import com.example.musicaapp.data.MusicRepository
import com.example.musicaapp.models.Album
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val repo = remember { MusicRepository() }
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var currentAlbum by remember { mutableStateOf<Album?>(null) }

    val scope = rememberCoroutineScope()

    // Llamar API al iniciar
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                albums = repo.fetchAlbums()
                if (albums.isNotEmpty()) {
                    currentAlbum = albums.first()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                loading = false
            }
        }
    }

    // Fondo degradado
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF4F1FF), Color(0xFFFFFFFF))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            HomeHeader("Max Ariciaga")

            if (loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                // --- SECCIÓN ALBUMS ---
                SectionHeader(title = "Albums")

                Spacer(Modifier.height(12.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    items(albums) { album ->
                        AlbumCard(
                            album,
                            onClick = {
                                if (album.id.isNotBlank()) {
                                    navController.navigate(Routes.detailWithId(album.id))
                                }
                            },
                            onPlayClick = { currentAlbum = album }
                        )
                    }
                }

                // --- SECCIÓN RECENTLY PLAYED ---
                Spacer(Modifier.height(28.dp))

                SectionHeader(title = "Recently Played")

                Spacer(Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(albums.take(8)) { album ->
                        RecentlyPlayedItem(album) { currentAlbum = album }
                    }
                }
            }
        }

        // --- MiniPlayer fijo ---
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            MiniPlayer(
                currentAlbum ?: Album(
                    id = "0",
                    title = "No song playing",
                    artist = "Select a song",
                    coverUrl = "https://music.juanfrausto.com/storage/cover/placeholder.jpg"
                )
            )
        }
    }
}

// Cabecera de sección con “See More”
@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            "See More",
            color = Color(0xFF6C42FF),
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp
        )
    }
}

// --- Tarjeta horizontal de álbumes ---
@Composable
fun AlbumCard(album: Album, onClick: () -> Unit, onPlayClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Box {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = album.title,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            IconButton(
                onClick = onPlayClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(42.dp)
                    .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.extraLarge)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Column(Modifier.padding(12.dp)) {
            Text(album.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(2.dp))
            Text(album.artist, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// --- Tarjeta vertical para “Recently Played” ---
@Composable
fun RecentlyPlayedItem(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = album.title,
                modifier = Modifier.size(65.dp) // Imagen más grande
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(album.title, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(2.dp))
                Text(
                    "${album.artist} • Popular Song",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }

            // Menú de tres puntos
            IconButton(onClick = { /* Acción futura */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.Gray
                )
            }
        }
    }
}
