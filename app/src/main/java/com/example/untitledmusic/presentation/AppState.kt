package com.example.untitledmusic.presentation

import com.example.untitledmusic.domain.entity.PlayBackStateEntity

enum class AppStatus {
    Idle,Loading,Success,Failed
}

data class AppState(
    val status: AppStatus = AppStatus.Idle,
    val player: PlayBackStateEntity? = null,
    val message: String = "",
    val playerState: AppPlayerState = AppPlayerState()
)

data class AppPlayerState(
    val isHavePlay : Boolean = false,
    val isPlaying: Boolean = false
)