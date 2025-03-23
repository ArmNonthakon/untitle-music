package com.example.feature_search_screen.presentation

import com.example.feature_search_screen.domain.entity.SearchEntity

enum class SearchStatus {
    Idle,Loading,Success,Failed
}

data class SearchState(
    val status: SearchStatus = SearchStatus.Idle,
    val message: String = "",
    val data: SearchEntity? = null
)
