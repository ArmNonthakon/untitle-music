package com.example.core.data.retrofit

import android.content.Context
import com.example.core.data.interceptor.AuthenticationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {
    private const val BASE_URL_ACCOUNT = "https://accounts.spotify.com/"

    private const val BASE_URL_API = "https://api.spotify.com/"

    @Provides
    @Singleton
    fun getOkhttpClient(@ApplicationContext context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
        return OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(context = context))
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("AccountRetrofit")
    fun getInstanceAccount(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_ACCOUNT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("ApiRetrofit")
    fun getInstanceApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}