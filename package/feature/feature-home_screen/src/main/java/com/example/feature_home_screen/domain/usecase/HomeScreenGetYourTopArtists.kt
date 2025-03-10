package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.entity.artist.artistYourTop.ArtistYourTopEntity
import com.example.feature_home_screen.domain.mapper.HomeScreenArtistMapper
import javax.inject.Inject

class HomeScreenGetYourTopArtists @Inject constructor(private val repository: HomeScreenRepository, private val mapper: HomeScreenArtistMapper){
    suspend fun execute(): Result<Any?> {
        try {
            val response = repository.getYourTopArtists()
            if (response.isSuccessful) {
                val artist = response.body()?.items?.map { mapper.map(it) }
                if(artist != null){
                    return Result.success(ArtistYourTopEntity(total = artist.size, items = artist))
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            println("error : $e")
            return Result.failure<Exception>(e)
        }
    }
}