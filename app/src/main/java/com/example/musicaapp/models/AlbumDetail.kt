package com.example.musicaapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumDetail(
    val id: String,
    val title: String,
    val artist: String,
    val description: String,
    @SerializedName("image") val coverUrl: String
) : Serializable
