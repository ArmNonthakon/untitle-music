package com.example.feature_search_screen.domain.entity

import com.example.core.data.model.Image
import com.example.core.data.model.search.Tracks

data class PlaylistEntity(
    val id: String?,
    val images: List<Image>,
    val name: String,
    val snapshotId: String,
    val tracks: Tracks,
    val uri: String
)
