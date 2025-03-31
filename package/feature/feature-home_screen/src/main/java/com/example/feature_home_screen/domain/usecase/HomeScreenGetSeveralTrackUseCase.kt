package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.entity.track.trackSeveral.TrackSeveralEntity
import com.example.feature_home_screen.domain.mapper.HomeScreenTrackMapper
import javax.inject.Inject

class HomeScreenGetSeveralTrackUseCase @Inject constructor(
    private val repository: HomeScreenRepository,
    private val mapper: HomeScreenTrackMapper
) {
    suspend fun execute(q: String): Result<Any?> {
        try {
            val response = repository.searchItems(q)
            if (response.isSuccessful) {
                val tracks = response.body()?.tracks?.items?.map { mapper.map(it) }
                if (tracks != null) {
                    return Result.success(TrackSeveralEntity(tracks))
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}