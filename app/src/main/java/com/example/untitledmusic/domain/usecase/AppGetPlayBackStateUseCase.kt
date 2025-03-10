package com.example.untitledmusic.domain.usecase

import com.example.untitledmusic.data.AppRepository
import com.example.untitledmusic.domain.mapper.PlayBackStateMapper
import javax.inject.Inject

class AppGetPlayBackStateUseCase @Inject constructor(private val repository: AppRepository,private val mapper: PlayBackStateMapper){
    suspend fun execute(): Result<Any?> {
        try {
            val response = repository.getPlayBackState()
            if (response.isSuccessful) {
                val playBackStateResponse = response.body()
                if(playBackStateResponse != null){
                    val result = mapper.mapper(response.body()!!)
                    return Result.success(result)
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