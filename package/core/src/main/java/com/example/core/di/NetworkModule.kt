package com.example.core.di

import com.example.core.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(@Named("ApiRetrofit") retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
}