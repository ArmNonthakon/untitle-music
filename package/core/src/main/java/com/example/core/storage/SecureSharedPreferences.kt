package com.example.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


object SecureSharedPreferences {
    @Volatile
    private var sharedPreferences: SharedPreferences? = null

    fun getInstance(context: Context): SharedPreferences {
        return sharedPreferences ?: synchronized(this) {
            sharedPreferences ?: createEncryptedSharedPreferences(context).also { sharedPreferences = it }
        }
    }

    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}