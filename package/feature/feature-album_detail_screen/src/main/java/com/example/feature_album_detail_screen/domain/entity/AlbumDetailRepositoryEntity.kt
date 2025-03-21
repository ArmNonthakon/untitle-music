package com.example.feature_album_detail_screen.domain.entity

import com.example.core.data.model.Image
import com.example.core.data.model.getAlbumResponse.ArtistAlbumResponse
import com.example.core.data.model.getAlbumResponse.TracksAlbumResponse

data class AlbumDetailEntity(
    val totalTracks: Int,
    val id: String,
    val images: List<Image>,
    val name: String,
    val uri: String,
    val artists: List<ArtistAlbumResponse>,
    val tracks: TracksAlbumResponse,
)

