package com.example.feature_home_screen.domain.entity.artist

import com.example.core.data.model.Followers
import com.example.core.data.model.Image

data class ArtistEntity(
    val followers: Followers,
    val href: String,
    val id: String,
    val image: List<Image>,
    val name: String,
    val type: String,
    val uri: String
)
