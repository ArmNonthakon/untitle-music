package com.example.core.utils

import android.content.Context
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SpotifyAppRemoteController @Inject constructor(){
    private var remote: SpotifyAppRemote? = null
    private val clientId = "ce98505416dc45cc92e85778734e85a4"
    private val redirectUri = "untitledmusic://callback"


    fun connectSpotify(@ApplicationContext context: Context) {
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
                    Log.d("SpotifyRemote", "Connected to Spotifyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
                }

                override fun onFailure(error: Throwable) {
                    Log.e("SpotifyRemote", "Failed to connect to Spotify", error)
                }
            })
    }

    fun playSong(uri: String) {
        remote?.playerApi?.play(uri)?.setResultCallback {
            Log.d("SpotifyRemote", "Playing song: $uri")
        }?.setErrorCallback { error ->
            Log.e("SpotifyRemote", "Error playing song", error)
        }
    }


    fun pauseSong() {
        remote?.playerApi?.pause()?.setResultCallback {
            Log.d("SpotifyRemote", "Playback paused")
        }?.setErrorCallback { error ->
            Log.e("SpotifyRemote", "Error pausing playback", error)
        }
    }

    fun resumeSong() {
        remote?.playerApi?.resume()?.setResultCallback {
            Log.d("SpotifyRemote", "Playback resumed")
        }?.setErrorCallback { error ->
            Log.e("SpotifyRemote", "Error resuming playback", error)
        }
    }

    fun skipNextSong() {
        remote?.playerApi?.skipNext()?.setResultCallback {
            Log.d("SpotifyRemote", "Skip to next song")
        }?.setErrorCallback {
            Log.d("SpotifyRemote", "Error to skip to next song")
        }
    }

    fun skipPreviousSong() {
        remote?.playerApi?.skipPrevious()?.setResultCallback {
            Log.d("SpotifyRemote", "Skip to previous song")
        }?.setErrorCallback {
            Log.d("SpotifyRemote", "Error to skip to previous song")
        }
    }

    fun setShuffleSong(isShuffle : Boolean) {
        remote?.playerApi?.setShuffle(isShuffle)?.setResultCallback {
            Log.d("SpotifyRemote", "Set shuffle song : $isShuffle")
        }?.setErrorCallback {
            Log.d("SpotifyRemote", "Error to set shuffle song")
        }
    }

    fun setRepeatSong(number : Int) {
        remote?.playerApi?.setRepeat(number)?.setResultCallback {
            Log.d("SpotifyRemote", "Set repeat song : $number")
        }?.setErrorCallback {
            Log.d("SpotifyRemote", "Error to set repeat song")
        }
    }

    fun disconnectSpotify() {
        remote?.let {
            SpotifyAppRemote.disconnect(it)
            Log.d("SpotifyRemote", "Disconnected from Spotify")
        }
    }

}
