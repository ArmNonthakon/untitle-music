package com.example.untitledmusic.data

import com.example.core.data.model.player.playBackState.PlayBackStateResponse
import retrofit2.Response

interface AppRepository {
    suspend fun getPlayBackState(): Response<PlayBackStateResponse>
}