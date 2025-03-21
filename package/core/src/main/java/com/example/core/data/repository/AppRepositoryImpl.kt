package com.example.core.data.repository

import com.example.core.data.model.player.playBackState.PlayBackStateResponse
import com.example.core.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val apiService: ApiService): AppRepository{
    override suspend fun getPlayBackState(): Response<PlayBackStateResponse> {
        return apiService.getPlayBackState()
    }
}