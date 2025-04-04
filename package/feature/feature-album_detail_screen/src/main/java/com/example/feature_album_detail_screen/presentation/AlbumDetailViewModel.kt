package com.example.feature_album_detail_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.entity.ArtistDetailEntity
import com.example.feature_album_detail_screen.domain.usecase.AlbumDetailGetAlbumUseCase
import com.example.feature_album_detail_screen.domain.usecase.AlbumDetailGetArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(private val getAlbumUseCase: AlbumDetailGetAlbumUseCase,private val getArtistUseCase: AlbumDetailGetArtistUseCase) :
    ViewModel() {
    private val albumDetailIntent = MutableSharedFlow<AlbumDetailIntent>()

    private val _albumDetailState = MutableStateFlow(AlbumDetailState())
    val albumDetailState: StateFlow<AlbumDetailState> = _albumDetailState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            albumDetailIntent.collectLatest { event ->
                when (event) {
                    is AlbumDetailIntent.GetAlbumResponse -> getAlbum(event.albumId)
                    is AlbumDetailIntent.GetArtistResponse -> getArtist(event.artistId)
                }
            }
        }
    }

    private fun getAlbum(albumId: String) {
        _albumDetailState.value = _albumDetailState.value.copy(status = AlbumDetailStatus.Loading)
        viewModelScope.launch {
            getAlbumUseCase.execute(albumId).onSuccess {
                if (it is AlbumDetailEntity) {
                    _albumDetailState.value = _albumDetailState.value.copy(
                        status = AlbumDetailStatus.Success,
                        data = it
                    )
                } else {
                    _albumDetailState.value = _albumDetailState.value.copy(
                        status = AlbumDetailStatus.Failed,
                        message = "No data"
                    )
                }
            }.onFailure {
                _albumDetailState.value = _albumDetailState.value.copy(
                    status = AlbumDetailStatus.Failed,
                    message = it.message
                )
            }
        }

    }

    private fun getArtist(artistId: String) {
        _albumDetailState.value = _albumDetailState.value.copy(status = AlbumDetailStatus.Loading)
        viewModelScope.launch {
            getArtistUseCase.execute(artistId).onSuccess {
                if (it is ArtistDetailEntity) {
                    _albumDetailState.value = _albumDetailState.value.copy(
                        status = AlbumDetailStatus.Success,
                        artist = it
                    )
                } else {
                    _albumDetailState.value = _albumDetailState.value.copy(
                        status = AlbumDetailStatus.Failed,
                        message = "No data"
                    )
                }
            }.onFailure {
                _albumDetailState.value = _albumDetailState.value.copy(
                    status = AlbumDetailStatus.Failed,
                    message = it.message
                )
            }
        }

    }

    fun sendIntent(intent: AlbumDetailIntent) {
        viewModelScope.launch {
            albumDetailIntent.emit(intent)
        }
    }
}