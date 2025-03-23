package com.example.feature_search_screen.data.repository

import com.example.core.data.model.search.SearchResponse
import com.example.core.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val service : ApiService) : SearchRepository {
    override suspend fun searchItems(q: String): Response<SearchResponse> {
        return service.searchItems(q = q)
    }
}