package com.example.musicaapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    @SerializedName("image") val coverUrl: String, // Cambio realizado aquí image para que te carge las imágenes
    val description: String? = null
) : Serializable
