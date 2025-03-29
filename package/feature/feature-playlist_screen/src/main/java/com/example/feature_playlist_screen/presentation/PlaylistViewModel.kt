package com.example.feature_playlist_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.ApiStatus
import com.example.feature_playlist_screen.domain.entity.PlaylistEntity
import com.example.feature_playlist_screen.domain.usecase.PlaylistGetPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylist: PlaylistGetPlaylistUseCase,
) : ViewModel() {
    private val playlistIntent = MutableSharedFlow<PlaylistIntent>()

    private val _playlistState = MutableStateFlow(PlaylistState())
    val playlistState : StateFlow<PlaylistState> = _playlistState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            playlistIntent.collectLatest { event ->
                when (event) {
                    is PlaylistIntent.GetPlaylist -> getPlaylist(event.playlistId)
                }
            }
        }
    }


    private fun getPlaylist(playlistId: String) {
        _playlistState.value = _playlistState.value.copy(status = ApiStatus.Loading)
        viewModelScope.launch {
            getPlaylist.execute(playlistId).onSuccess {
                if (it is PlaylistEntity) {
                    _playlistState.value = _playlistState.value.copy(
                        status = ApiStatus.Success,
                        data = it
                    )
                } else {
                    _playlistState.value = _playlistState.value.copy(
                        status = ApiStatus.Failed,
                        message = "No data response"
                    )
                }
            }.onFailure {
                _playlistState.value = _playlistState.value.copy(
                    status = ApiStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }



    fun sendIntent(intent: PlaylistIntent) {
        viewModelScope.launch {
            playlistIntent.emit(intent)
        }
    }
}
