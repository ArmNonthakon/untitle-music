package com.example.core.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppSpotifyAppRemote : ViewModel() {
    private var spotifyAppRemote: SpotifyAppRemote? = null
    private val clientId = "ce98505416dc45cc92e85778734e85a4"
    private val redirectUri = "untitledmusic://callback"

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    fun connectSpotify(context: Context) {
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                _isConnected.value = true
                Log.d("SpotifyRemote", "Connected to Spotify")
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyRemote", "Connection failed", throwable)
                _isConnected.value = false
            }
        })
    }

    fun playSong(uri: String) {
        spotifyAppRemote?.playerApi?.play(uri)
            ?: Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
    }

    fun pauseSong() {
        spotifyAppRemote?.playerApi?.pause()
            ?: Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
    }

    fun resumeSong() {
        spotifyAppRemote?.playerApi?.resume()
            ?: Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
    }

    fun disconnectSpotify() {
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
            _isConnected.value = false
            Log.d("SpotifyRemote", "Disconnected from Spotify")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectSpotify()
    }
}
