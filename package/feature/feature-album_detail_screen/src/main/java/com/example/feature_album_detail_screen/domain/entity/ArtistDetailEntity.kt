package com.example.feature_album_detail_screen.domain.entity

import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Followers
import com.example.core.data.model.Image
import com.google.gson.annotations.SerializedName

data class ArtistDetailEntity(
    val id: String,
    val images: List<Image>,
)

