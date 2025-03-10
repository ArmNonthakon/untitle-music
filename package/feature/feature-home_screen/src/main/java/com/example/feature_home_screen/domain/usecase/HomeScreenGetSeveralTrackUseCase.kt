package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.entity.album.albumNewReleases.AlbumNewReleasesEntity
import com.example.feature_home_screen.domain.entity.track.trackSeveral.TrackSeveralEntity
import com.example.feature_home_screen.domain.mapper.HomeScreenTrackMapper
import javax.inject.Inject

class HomeScreenGetSeveralTrackUseCase @Inject constructor(private val repository: HomeScreenRepository,private val mapper: HomeScreenTrackMapper){
    suspend fun execute(): Result<Any?> {
        try {
            val response = repository.getSeveralTracks()
            if (response.isSuccessful) {
                val tracks = response.body()?.tracks?.map { mapper.map(it) }
                if(tracks != null){
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