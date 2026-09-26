package com.example.musicaapp.remote

import com.example.musicaapp.models.Album
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("search")
    suspend fun getAlbums(
        @Query("term") term: String = "rock",
        @Query("entity") entity: String = "album",
        @Query("limit") limit: Int = 25
    ): ITunesResponse
}

data class ITunesResponse(
    val resultCount: Int,
    val results: List<Album>
)