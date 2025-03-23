package com.example.feature_search_screen.data.repository

import com.example.core.data.model.search.SearchResponse
import retrofit2.Response

interface SearchRepository {
    suspend fun searchItems(q : String) : Response<SearchResponse>
}