package com.example.musicaapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    @SerializedName("image") val coverUrl: String, // 👈 Cambiado de "cover" a "image"
    val description: String? = null
) : Serializable
