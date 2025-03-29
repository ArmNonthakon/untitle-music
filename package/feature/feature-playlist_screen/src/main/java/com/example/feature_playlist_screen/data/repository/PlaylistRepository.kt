package com.example.feature_playlist_screen.data.repository

import com.example.core.data.model.playlist.PlaylistResponse
import retrofit2.Response

sealed interface PlaylistRepository {
    suspend fun getPlaylist(playlistId : String) : Response<PlaylistResponse>
}