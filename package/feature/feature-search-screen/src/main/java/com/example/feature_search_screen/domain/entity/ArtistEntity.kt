package com.example.feature_search_screen.domain.entity

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
