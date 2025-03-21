package com.example.core.domain.entity

import com.example.core.data.model.Actions
import com.example.core.data.model.Context
import com.example.core.data.model.Device
import com.example.core.data.model.track.TrackResponse


data class PlayBackStateEntity(
    val device: Device,
    val repeatState: String,
    val shuffleState: Boolean,
    val context: Context?,
    val timestamp: Long,
    val progressMs: Long,
    val durationMs: Int?,
    val isPlaying: Boolean,
    val item: TrackResponse?,
    val currentlyPlayingType: String,
    val actions: Actions
)
