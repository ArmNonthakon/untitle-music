package com.example.feature_search_screen.di

import com.example.feature_search_screen.data.repository.SearchRepository
import com.example.feature_search_screen.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchModule {
    @Binds
    abstract fun bindHomeScreenRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}
