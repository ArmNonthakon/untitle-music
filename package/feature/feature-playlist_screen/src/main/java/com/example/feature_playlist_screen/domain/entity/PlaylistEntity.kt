package com.example.feature_playlist_screen.domain.entity

import com.example.core.data.model.Image
import com.example.core.data.model.Owner
import com.example.core.data.model.playlist.TracksPlaylistResponse


data class PlaylistEntity(
    val description: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val tracks: TracksPlaylistResponse,
    val uri: String
)
