package com.example.feature_album_detail_screen.presentation

import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.entity.ArtistDetailEntity

enum class AlbumDetailStatus {
    Idle,Loading,Success,Failed
}

data class AlbumDetailState(
    val status: AlbumDetailStatus = AlbumDetailStatus.Idle,
    val data: AlbumDetailEntity? = null,
    val artist: ArtistDetailEntity? = null,
    val message: String? = null
)


