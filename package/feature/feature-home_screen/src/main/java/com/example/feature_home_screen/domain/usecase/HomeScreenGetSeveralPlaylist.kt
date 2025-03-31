package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.mapper.HomeScreenPlaylistMapper
import javax.inject.Inject

class HomeScreenGetSeveralPlaylist @Inject constructor(
    private val repository: HomeScreenRepository,
    private val mapper: HomeScreenPlaylistMapper
) {
    suspend fun execute(q: String): Result<Any?> {
        try {
            val response = repository.searchItems(q)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val playlist = mapper.map(body.playlists)
                    return Result.success(playlist)
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}