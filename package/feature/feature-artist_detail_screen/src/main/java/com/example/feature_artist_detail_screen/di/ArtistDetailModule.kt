package com.example.feature_artist_detail_screen.di

import com.example.feature_artist_detail_screen.data.repository.ArtistDetailRepositoryImpl
import com.example.feature_artist_detail_screen.data.repository.ArtistDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArtistDetailModule {
    @Binds
    abstract fun bindHomeScreenRepository(
        artistDetailRepositoryImpl: ArtistDetailRepositoryImpl
    ): ArtistDetailRepository
}
