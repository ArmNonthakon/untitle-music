package com.example.feature_search_screen.presentation

sealed interface SearchIntent {
    data class SearchItems(val q : String) : SearchIntent
    data object InitialState : SearchIntent
}