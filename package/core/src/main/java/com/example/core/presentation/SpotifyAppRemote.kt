package com.example.core.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppSpotifyAppRemote @Inject constructor() : ViewModel() {
    private var remote: SpotifyAppRemote? = null
    private val clientId = "ce98505416dc45cc92e85778734e85a4"
    private val redirectUri = "untitledmusic://callback"

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    private val _isHavePlayer = MutableStateFlow(false)
    val isHavePlayer : StateFlow<Boolean> = _isHavePlayer

    fun connectSpotify(context: Context) {
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(
            context,
            connectionParams,
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    remote = spotifyAppRemote
                    _isConnected.value = true
                    Log.d("SpotifyRemote", "Connected to Spotify")
                }

                override fun onFailure(error: Throwable) {
                    _isConnected.value = false
                    Log.e("SpotifyRemote", "Failed to connect to Spotify", error)
                }
            })
    }

    fun playSong(uri: String) {
        if (_isConnected.value) {
            remote?.playerApi?.play(uri)?.setResultCallback {
                Log.d("SpotifyRemote", "Playing song: $uri")
                _isPlaying.value = true
                _isHavePlayer.value = true
            }?.setErrorCallback { error ->
                Log.e("SpotifyRemote", "Error playing song", error)
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }


    fun pauseSong() {
        if (_isConnected.value) {
            remote?.playerApi?.pause()?.setResultCallback {
                Log.d("SpotifyRemote", "Playback paused")
                _isPlaying.value = false
            }?.setErrorCallback { error ->
                Log.e("SpotifyRemote", "Error pausing playback", error)
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }

    fun resumeSong() {
        if (_isConnected.value) {
            remote?.playerApi?.resume()?.setResultCallback {
                Log.d("SpotifyRemote", "Playback resumed")
                _isPlaying.value = true
            }?.setErrorCallback { error ->
                Log.e("SpotifyRemote", "Error resuming playback", error)
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }

    fun skipNextSong() {
        if (_isConnected.value) {
            remote?.playerApi?.skipNext()?.setResultCallback {
                Log.d("SpotifyRemote", "Skip to next song")
            }?.setErrorCallback {
                Log.d("SpotifyRemote", "Error to skip to next song")
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }

    fun skipPreviousSong() {
        if (_isConnected.value) {
            remote?.playerApi?.skipPrevious()?.setResultCallback {
                Log.d("SpotifyRemote", "Skip to previous song")
            }?.setErrorCallback {
                Log.d("SpotifyRemote", "Error to skip to previous song")
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }

    fun setShuffleSong() {
        if (_isConnected.value) {
            remote?.playerApi?.setShuffle(true)?.setResultCallback {
                Log.d("SpotifyRemote", "Skip to previous song")
            }?.setErrorCallback {
                Log.d("SpotifyRemote", "Error to skip to previous song")
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }

    fun setRepeatSong() {
        if (_isConnected.value) {
            remote?.playerApi?.setRepeat(0)?.setResultCallback {
                Log.d("SpotifyRemote", "Skip to previous song")
            }?.setErrorCallback {
                Log.d("SpotifyRemote", "Error to skip to previous song")
            }
        } else {
            Log.e("SpotifyRemote", "SpotifyAppRemote is not connected")
        }
    }

    private fun disconnectSpotify() {
        if (_isConnected.value) {
            remote?.let {
                SpotifyAppRemote.disconnect(it)
                _isConnected.value = false
                Log.d("SpotifyRemote", "Disconnected from Spotify")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectSpotify()
    }
}
