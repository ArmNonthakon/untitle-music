package com.example.feature_album_detail_screen.domain.usecase

import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepository
import com.example.feature_album_detail_screen.domain.mapper.ArtistDetailMapper
import javax.inject.Inject

class AlbumDetailGetArtistUseCase @Inject constructor(
    private val repository: AlbumDetailRepository,
    private val mapper : ArtistDetailMapper,
) {
    suspend fun execute(artistId : String): Result<Any?> {
        try {
            val response = repository.getArtist(artistId)
            if (response.isSuccessful) {
                val artist = response.body();
                if (artist != null) {
                    val result = mapper.mapper(artist)
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
