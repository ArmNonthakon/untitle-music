package com.example.feature_search_screen.domain.entity

import com.example.core.data.model.ExternalIds
import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse


data class TrackEntity(
    val album: AlbumResponse,
    val artists: List<ArtistResponse>,
    val id: String,
    val isPlayable: Boolean,
    val name: String,
    val uri: String,
)

