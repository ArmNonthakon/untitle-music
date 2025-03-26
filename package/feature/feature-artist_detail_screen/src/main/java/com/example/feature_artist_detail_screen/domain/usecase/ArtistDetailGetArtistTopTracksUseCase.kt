package com.example.feature_artist_detail_screen.domain.usecase

import com.example.feature_artist_detail_screen.data.repository.ArtistDetailRepository
import com.example.feature_artist_detail_screen.domain.entity.TopTracksEntity
import com.example.feature_artist_detail_screen.domain.mapper.TopTracksMapper
import javax.inject.Inject

class ArtistDetailGetArtistTopTracksUseCase @Inject constructor(private val repository: ArtistDetailRepository,private val mapper: TopTracksMapper){
    suspend fun execute(artistId : String): Result<Any?> {
        try {
            val response = repository.getArtistTopTracks(artistId)
            if (response.isSuccessful) {
                val tracks = response.body()?.tracks
                if (tracks != null) {
                    println(tracks)
                    val result = tracks.map { mapper.mapper(it) }
                    return Result.success(TopTracksEntity(items = result))
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}