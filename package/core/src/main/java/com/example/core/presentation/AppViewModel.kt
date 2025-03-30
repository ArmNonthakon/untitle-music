package com.example.core.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.entity.PlayBackStateEntity
import com.example.core.domain.usecase.AppGetPlayBackStateUseCase
import com.example.core.utils.SpotifyAppRemoteController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val getPlayBackStateUseCase: AppGetPlayBackStateUseCase, private val spotifyAppRemote: SpotifyAppRemoteController, @ApplicationContext context: Context) : ViewModel(){
    private val appIntent = MutableSharedFlow<AppIntent>()

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState
    private var job: Job? = null

    init {
        handleIntent()
        spotifyAppRemote.connectSpotify(context)
    }

    private fun handleIntent() {
        viewModelScope.launch {
            appIntent.collectLatest { event ->
                when (event) {
                    AppIntent.GetPlayBackState -> getPlayBackStateEvent()
                    is AppIntent.PlaySong -> playSongEvent(event.uri)
                    AppIntent.ResumePauseSong -> resumePauseSongEvent()
                    AppIntent.NextSong -> nextSongEvent()
                    AppIntent.PreviousSong -> previousSongEvent()
                    else -> println("No event")
                }
            }
        }
    }
    private fun getPlayBackStateEvent() {
        _appState.value = _appState.value.copy(status = AppStatus.Loading)
        viewModelScope.launch {
            getPlayBackStateUseCase.execute().onSuccess {
                if (it is PlayBackStateEntity) {
                    _appState.value = _appState.value.copy(
                        status = AppStatus.Success,
                        player = it,
                        playerState = AppPlayerState(isPlaying = it.isPlaying, isHavePlay = true)
                    )
                    startSongProgress()
                } else {
                    _appState.value = _appState.value.copy(
                        status = AppStatus.Failed,
                        message = "No data"
                    )
                }
            }.onFailure {
                _appState.value = _appState.value.copy(
                    status = AppStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    private fun startSongProgress() {
        job?.cancel()
        job = viewModelScope.launch {
            while (_appState.value.playerState.isPlaying) {
                val currentPlayer = _appState.value.player
                if (currentPlayer == null || currentPlayer.progressMs >= currentPlayer.durationMs!!) break

                delay(1000L)

                _appState.value = _appState.value.copy(
                    player = currentPlayer.copy(progressMs = currentPlayer.progressMs + 1000)
                )
            }

            if ((_appState.value.player?.progressMs ?: 0) >= (_appState.value.player?.durationMs
                    ?: 0)
            ) {
                getPlayBackStateEvent()
            }
        }
    }


    private fun playSongEvent(uri: String) {
        viewModelScope.launch {
            spotifyAppRemote.playSong(uri)
            delay(timeMillis = 1500)
            _appState.value = _appState.value.copy(playerState = _appState.value.playerState.copy(isHavePlay = true,isPlaying = true))
            getPlayBackStateEvent()
        }
    }

    private fun resumePauseSongEvent() {
        viewModelScope.launch {
            if(_appState.value.playerState.isPlaying){
                spotifyAppRemote.pauseSong()
                _appState.value = _appState.value.copy(playerState = _appState.value.playerState.copy(isPlaying = false))
            }else{
                spotifyAppRemote.resumeSong()
                _appState.value = _appState.value.copy(playerState = _appState.value.playerState.copy(isPlaying = true))
                startSongProgress()
            }
        }
    }

    private fun nextSongEvent() {
        viewModelScope.launch {
            spotifyAppRemote.skipNextSong()
            delay(timeMillis = 1000)
            getPlayBackStateEvent()
            _appState.value = _appState.value.copy(playerState = _appState.value.playerState.copy(isHavePlay = true,isPlaying = true))
        }
    }

    private fun previousSongEvent() {
        viewModelScope.launch {
            spotifyAppRemote.skipPreviousSong()
            delay(timeMillis = 1000)
            getPlayBackStateEvent()
            _appState.value = _appState.value.copy(playerState = _appState.value.playerState.copy(isHavePlay = true,isPlaying = true))
        }
    }


    fun sendIntent(intent: AppIntent) {
        viewModelScope.launch {
            appIntent.emit(intent)
        }
    }

    override fun onCleared() {
        super.onCleared()
        spotifyAppRemote.disconnectSpotify()
    }
}