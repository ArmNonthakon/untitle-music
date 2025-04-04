package com.example.feature_album_detail_screen.domain.usecase

import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepository
import com.example.feature_album_detail_screen.domain.mapper.AlbumDetailMapper
import javax.inject.Inject

class AlbumDetailGetAlbumUseCase @Inject constructor(
    private val repository: AlbumDetailRepository,
    private val mapper: AlbumDetailMapper
) {
    suspend fun execute(albumId : String): Result<Any?> {
        try {
            val response = repository.getAlbum(albumId)
            if (response.isSuccessful) {
                val album = response.body();
                if (album != null) {
                    val result = mapper.mapper(album)
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

