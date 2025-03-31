package com.example.feature_home_screen.domain.entity.playlist

import com.example.core.data.model.Image
import com.example.core.data.model.Owner


data class PlaylistSeveralEntity(
    val items: List<PlaylistEntity?>
)


data class PlaylistEntity(
    val description: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val uri: String
)
