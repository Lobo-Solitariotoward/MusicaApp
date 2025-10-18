package com.example.musicaapp.data

import com.example.musicaapp.models.Album
import com.example.musicaapp.remote.MusicApi
import com.example.musicaapp.remote.NetworkModule

class MusicRepository(
    private val api: MusicApi = NetworkModule.api
) {
    suspend fun fetchAlbums(): List<Album> = api.getAlbums()

    suspend fun fetchAlbum(id: String): Album {
        val albums = api.getAlbums()
        return albums.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("El álbum que buscas no está disponible con $id")
    }
}
