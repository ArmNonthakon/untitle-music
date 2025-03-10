package com.example.feature_home_screen.domain.entity.album

import com.example.core.data.model.Image


data class AlbumEntity(
    val totalTracks: Int,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val uri: String,
)


