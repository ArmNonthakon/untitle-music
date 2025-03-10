package com.example.core.data.model.player.playBackState

import com.example.core.data.model.Actions
import com.example.core.data.model.Context
import com.example.core.data.model.Device
import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

data class PlayBackStateResponse(
    @SerializedName("device") val device: Device,
    @SerializedName("repeat_state") val repeatState: String,
    @SerializedName("shuffle_state") val shuffleState: Boolean,
    @SerializedName("context") val context: Context?,
    @SerializedName("timestamp") val timestamp: Long,
    @SerializedName("progress_ms") val progressMs: Long,
    @SerializedName("is_playing") val isPlaying: Boolean,
    @SerializedName("item") val item: TrackResponse?,
    @SerializedName("currently_playing_type") val currentlyPlayingType: String,
    @SerializedName("actions") val actions: Actions
)





