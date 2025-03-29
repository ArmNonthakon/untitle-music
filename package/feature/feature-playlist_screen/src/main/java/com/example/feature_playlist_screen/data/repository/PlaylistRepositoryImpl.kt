package com.example.feature_playlist_screen.data.repository

import com.example.core.data.model.playlist.PlaylistResponse
import com.example.core.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(private val service: ApiService) :
    PlaylistRepository {
    override suspend fun getPlaylist(playlistId: String): Response<PlaylistResponse> {
        return service.getPlaylist(playlistId)
    }
}