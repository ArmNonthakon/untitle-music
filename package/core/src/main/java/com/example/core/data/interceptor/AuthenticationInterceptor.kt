package com.example.core.data.interceptor

import android.content.Context
import com.example.core.storage.SecureSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val secureStorage = SecureSharedPreferences.getInstance(context)
        val accessToken = secureStorage.getString("access_token", null)


        val requestBuilder = chain.request().newBuilder()

        if (!accessToken.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(requestBuilder.build())
    }
}
