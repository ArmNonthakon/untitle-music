package com.example.feature_playlist_screen.presentation

import com.example.core.utils.ApiStatus
import com.example.feature_playlist_screen.domain.entity.PlaylistEntity

data class PlaylistState(
    val status: ApiStatus = ApiStatus.Idle,
    val message: String = "",
    val data: PlaylistEntity? = null
)
