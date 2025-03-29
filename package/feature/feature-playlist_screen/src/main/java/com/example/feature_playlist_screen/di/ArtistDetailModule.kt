package com.example.feature_playlist_screen.di

import com.example.feature_playlist_screen.data.repository.PlaylistRepository
import com.example.feature_playlist_screen.data.repository.PlaylistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaylistModule {
    @Binds
    abstract fun bindHomeScreenRepository(
        playlistRepositoryImpl: PlaylistRepositoryImpl
    ): PlaylistRepository
}
