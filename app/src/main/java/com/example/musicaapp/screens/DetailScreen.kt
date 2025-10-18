package com.example.musicaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun DetailScreen(navController: NavController, albumId: String) {
    val repo = remember { MusicRepository() }
    var albumDetail by remember { mutableStateOf<Album?>(null) } // ✅ solo Album, no AlbumDetail
    var loading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                albumDetail = repo.fetchAlbum(albumId) // ✅ fetchAlbum devuelve un Album
            } catch (e: Exception) {
                e.printStackTrace()
                albumDetail = null // evita crash si no se encuentra
            } finally {
                loading = false
            }
        }
    }


    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        albumDetail?.let { album ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFF4F1FF), Color.White)
                        )
                    )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp)
                ) {
                    item {
                        // --- Contenedor principal del álbum ---
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                shape = RoundedCornerShape(28.dp),
                                elevation = CardDefaults.cardElevation(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFF4F1FF))
                                ) {
                                    AsyncImage(
                                        model = album.coverUrl,
                                        contentDescription = album.title,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(320.dp)
                                            .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    // --- Gradiente oscuro inferior ---
                                    Box(
                                        modifier = Modifier
                                            .matchParentSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(Color.Transparent, Color(0xB3000000))
                                                )
                                            )
                                    )

                                    // --- Botones arriba (atrás y favorito) ---
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        IconButton(
                                            onClick = { navController.popBackStack() },
                                            modifier = Modifier
                                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                                .size(40.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back",
                                                tint = Color.White
                                            )
                                        }

                                        IconButton(
                                            onClick = { /* Favorito */ },
                                            modifier = Modifier
                                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                                .size(40.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.FavoriteBorder,
                                                contentDescription = "Favorite",
                                                tint = Color.White
                                            )
                                        }
                                    }

                                    // --- Texto y botones al fondo ---
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(20.dp)
                                    ) {
                                        Text(
                                            text = album.title,
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = album.artist,
                                            color = Color.White.copy(alpha = 0.9f),
                                            fontSize = 16.sp
                                        )

                                        Spacer(Modifier.height(20.dp))

                                        // --- Botones circulares de Play y Shuffle ---
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Botón morado (Play)
                                            IconButton(
                                                onClick = { /* Play */ },
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .background(Color(0xFF7D5CFF), CircleShape)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.PlayArrow,
                                                    contentDescription = "Play",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }

                                            Spacer(Modifier.width(16.dp))

                                            // Botón blanco (Shuffle)
                                            IconButton(
                                                onClick = { /* Shuffle */ },
                                                modifier = Modifier
                                                    .size(60.dp)
                                                    .background(Color.White, CircleShape)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.PlayArrow,
                                                    contentDescription = "Shuffle",
                                                    tint = Color.Black,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        // --- Card: About this album ---
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(6.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    "About this album",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(8.dp))
                                album.description?.let {
                                    Text(it, color = Color.DarkGray, fontSize = 14.sp)
                                }
                            }
                        }

                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Artist: ${album.artist}",
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 20.dp)
                        )

                        Spacer(Modifier.height(16.dp))
                    }

                    // --- Lista de canciones (mock) ---
                    items((1..10).map { "Track $it" }) { track ->
                        SongItem(
                            imageUrl = album.coverUrl,
                            title = "${album.title} • $track",
                            artist = album.artist
                        )
                    }
                }
                // --- 🎧 MiniPlayer fijo ---
                // --- 🎧 MiniPlayer fijo ---
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    MiniPlayer(
                        currentAlbum = com.example.musicaapp.models.Album(
                            id = album.id,
                            title = album.title,
                            artist = album.artist,
                            coverUrl = album.coverUrl,
                            description = album.description
                        )
                    )
                }

            }
        }
    }
}

// --- Canción individual ---
@Composable
fun SongItem(imageUrl: String, title: String, artist: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(
                    "$artist • Popular Song",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
            IconButton(onClick = { /* menú o más opciones */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = Color.Gray
                )
            }
        }
    }
}
