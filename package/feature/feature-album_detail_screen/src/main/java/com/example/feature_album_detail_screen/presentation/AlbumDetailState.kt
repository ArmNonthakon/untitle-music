package com.example.feature_album_detail_screen.presentation

import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity

enum class AlbumDetailStatus {
    Idle,Loading,Success,Failed
}

data class AlbumDetailState(
    val status: AlbumDetailStatus = AlbumDetailStatus.Idle,
    val data: AlbumDetailEntity? = null,
    val message: String? = null
)


