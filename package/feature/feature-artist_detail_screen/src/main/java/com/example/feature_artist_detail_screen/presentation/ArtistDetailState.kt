package com.example.feature_artist_detail_screen.presentation

import com.example.feature_artist_detail_screen.domain.entity.ArtistDetailEntity
import com.example.feature_artist_detail_screen.domain.entity.TopTracksEntity

enum class ArtistDetailStatus {
    Idle,Loading,Success,Failed
}

data class ArtistDetailState(
    val status: ArtistDetailStatus = ArtistDetailStatus.Idle,
    val artist: ArtistDetailEntity? = null,
    val topTracks : TopTracksEntity? = null,
    val message: String? = null
)


