package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.entity.track.trackYourTop.TrackYourTopEntity
import com.example.feature_home_screen.domain.mapper.HomeScreenTrackMapper
import javax.inject.Inject

class HomeScreenGetYourTopTracks @Inject constructor(private val repository: HomeScreenRepository, private val mapper: HomeScreenTrackMapper){
    suspend fun execute(): Result<Any?> {
        try {
            val response = repository.getYourTopTracks()
            if (response.isSuccessful) {
                val tracks = response.body()?.items?.map { mapper.map(it) }
                if(tracks != null){
                    return Result.success(TrackYourTopEntity(total = tracks.size, items = tracks))
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}