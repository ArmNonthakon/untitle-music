package com.example.feature_home_screen.domain.usecase

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.domain.mapper.HomeScreenUserMapper
import javax.inject.Inject

class HomeScreenGetUserUserCase @Inject constructor (
    private val repository: HomeScreenRepository,
    private val mapper: HomeScreenUserMapper
) {
    suspend fun execute(): Result<Any?> {
        try {
            val response = repository.getUserDetail()
            if (response.isSuccessful) {
                val entity = response.body()?.let { mapper.map(userResponse = it) }
                return Result.success(entity)
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}