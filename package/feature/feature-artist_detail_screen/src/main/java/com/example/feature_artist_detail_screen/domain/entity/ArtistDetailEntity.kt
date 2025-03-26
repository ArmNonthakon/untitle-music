package com.example.feature_artist_detail_screen.domain.entity

import com.example.core.data.model.Followers
import com.example.core.data.model.Image

data class ArtistDetailEntity(
    val id: String,
    val name : String,
    val images: List<Image>,
    val follower : Followers,
    val uri : String,
)

