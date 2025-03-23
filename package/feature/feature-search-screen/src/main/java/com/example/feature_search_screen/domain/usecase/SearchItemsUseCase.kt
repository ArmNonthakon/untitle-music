package com.example.feature_search_screen.domain.usecase

import com.example.feature_search_screen.data.repository.SearchRepository
import com.example.feature_search_screen.domain.mapper.SearchItemsMapper
import javax.inject.Inject

class SearchItemsUseCase @Inject constructor(
    private val repository: SearchRepository,
    private val mapper : SearchItemsMapper
) {
    suspend fun execute(q :String): Result<Any?> {
        try {
            val response = repository.searchItems(q)
            if (response.isSuccessful) {
                val body = response.body()?.let { mapper.mapper(it) }
                if(body != null){
                    return Result.success(body)
                }
                return Result.failure<Exception>(Exception("No data response"))
            }
            return Result.failure<Exception>(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure<Exception>(e)
        }
    }
}