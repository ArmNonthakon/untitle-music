package com.example.untitledmusic.domain.mapper

import com.example.core.data.model.player.playBackState.PlayBackStateResponse
import com.example.untitledmusic.domain.entity.PlayBackStateEntity
import javax.inject.Inject

class PlayBackStateMapper @Inject constructor() {
    fun mapper(playBackStateResponse: PlayBackStateResponse): PlayBackStateEntity? {
        return PlayBackStateEntity(
            device = playBackStateResponse.device,
            repeatState = playBackStateResponse.repeatState,
            shuffleState = playBackStateResponse.shuffleState,
            context = playBackStateResponse.context,
            timestamp = playBackStateResponse.timestamp,
            progressMs = playBackStateResponse.progressMs,
            durationMs = playBackStateResponse.item?.durationMs,
            isPlaying = playBackStateResponse.isPlaying,
            item = playBackStateResponse.item,
            currentlyPlayingType = playBackStateResponse.currentlyPlayingType,
            actions = playBackStateResponse.actions
        )
    }
}