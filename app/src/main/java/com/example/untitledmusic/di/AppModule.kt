package com.example.untitledmusic.di

import com.example.feature_home_screen.data.repository.HomeScreenRepository
import com.example.feature_home_screen.data.repository.HomeScreenRepositoryImpl
import com.example.untitledmusic.data.AppRepository
import com.example.untitledmusic.data.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository
}