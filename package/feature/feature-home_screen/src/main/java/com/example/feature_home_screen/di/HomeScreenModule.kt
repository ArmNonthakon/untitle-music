package com.example.feature_home_screen.di

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.data.repository.HomeScreenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeScreenModule {
    @Binds
    abstract fun bindHomeScreenRepository(
        homeScreenRepositoryImpl: HomeScreenRepositoryImpl
    ): HomeScreenRepository
}
