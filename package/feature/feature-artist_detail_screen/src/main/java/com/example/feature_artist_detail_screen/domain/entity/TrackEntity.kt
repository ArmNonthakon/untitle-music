package com.example.feature_artist_detail_screen.domain.entity

import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse

data class TrackEntity(
    val album: AlbumResponse,
    val artists: List<ArtistResponse>,
    val id: String,
    val name: String,
    val uri: String,
    val durationMs : Int,
)
