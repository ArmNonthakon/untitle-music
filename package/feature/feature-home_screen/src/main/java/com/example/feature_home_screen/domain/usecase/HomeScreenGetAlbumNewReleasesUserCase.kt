package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.entity.album.albumNewReleases.AlbumNewReleasesEntity
import com.example.feature_home_screen.domain.mapper.HomeScreenAlbumMapper
import javax.inject.Inject


class HomeScreenGetAlbumNewReleasesUserCase @Inject constructor(
    private val repository: HomeScreenRepository,
    private val mapper: HomeScreenAlbumMapper
) {
    suspend fun execute(): Result<Any?> {
        try {
            val response = repository.getAlbumNewRelease()
            if (response.isSuccessful) {
                val albums = response.body()?.albums?.items?.map { mapper.map(it) }
                if (albums != null) {
                    val entity = AlbumNewReleasesEntity(albums)
                    return Result.success(entity)
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}