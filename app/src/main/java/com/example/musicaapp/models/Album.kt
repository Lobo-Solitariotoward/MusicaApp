package com.example.musicaapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album(
    @SerializedName("collectionId") val id: String,
    @SerializedName("collectionName") val title: String,
    @SerializedName("artistName") val artist: String,
    @SerializedName("artworkUrl100") val coverUrl: String,
    val description: String? = null
) : Serializable
