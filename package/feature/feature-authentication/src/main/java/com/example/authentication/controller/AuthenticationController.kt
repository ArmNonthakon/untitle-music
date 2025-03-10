package com.example.authentication.controller

import android.annotation.SuppressLint
import android.content.Context
import com.example.authentication.data.service.AuthenticationService
import com.example.core.data.retrofit.RetrofitInstance
import com.example.core.storage.SecureSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class AuthenticationController(context: Context) {
    private val service = RetrofitInstance.getInstanceAccount().create(AuthenticationService::class.java)
    private val secureStorage = SecureSharedPreferences.getInstance(context)

    @SuppressLint("ApplySharedPref")
    suspend fun getAccessToken(code: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getAccessToken(code = code)
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        secureStorage.edit().apply {
                            putString("access_token", it.accessToken)
                            putString("refresh_token", it.refreshToken)
                            putLong("expires_in", Instant.now().plusSeconds(it.expiresIn.toLong()).epochSecond)
                            apply()
                        }
                    } ?: throw Exception("No response body found")
                } else {
                    throw Exception("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                throw Exception("Failed to get access token: ${e.message}", e)
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    suspend fun refreshAccessToken(refreshToken: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.refreshAccessToken(refreshToken = refreshToken)
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        secureStorage.edit().apply {
                            putString("access_token", it.accessToken)
                            putString("expires_in", it.expiresIn.toString())
                            apply()
                        }
                    } ?: throw Exception("No response body found")
                } else {
                    throw Exception("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                throw Exception("Failed to refresh access token: ${e.message}", e)
            }
        }
    }
}
