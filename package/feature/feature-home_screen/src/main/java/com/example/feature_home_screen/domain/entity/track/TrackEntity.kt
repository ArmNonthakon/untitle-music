package com.example.feature_home_screen.domain.entity.track

import com.example.core.data.model.ExternalIds
import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse


data class TrackEntity(
    val album: AlbumResponse,
    val artists: List<ArtistResponse>,
    val discNumber: Int,
    val durationMs: Int,
    val externalIds: ExternalIds,
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val isPlayable: Boolean,
    val name: String,
    val popularity: Int,
    val trackNumber: Int,
    val type: String,
    val uri: String,
)

