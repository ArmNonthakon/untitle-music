package com.example.feature_playlist_screen.domain.usecase

import com.example.feature_playlist_screen.data.repository.PlaylistRepository
import com.example.feature_playlist_screen.domain.mapper.PlaylistMapper
import javax.inject.Inject

class PlaylistGetPlaylistUseCase @Inject constructor(private val repository: PlaylistRepository,private val mapper: PlaylistMapper) {
    suspend fun execute(playlistId : String): Result<Any?> {
        try {
            val response = repository.getPlaylist(playlistId)
            if (response.isSuccessful) {
                val playlist = response.body()
                println(playlist?.tracks)
                if (playlist != null) {
                    val result = mapper.mapper(playlist)
                    println(result.tracks)
                    return Result.success(result)
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}