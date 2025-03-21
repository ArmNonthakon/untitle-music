package com.example.feature_album_detail_screen.di

import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepository
import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlbumDetailModule {
    @Binds
    abstract fun bindHomeScreenRepository(
        albumDetailRepositoryImpl: AlbumDetailRepositoryImpl
    ): AlbumDetailRepository
}
