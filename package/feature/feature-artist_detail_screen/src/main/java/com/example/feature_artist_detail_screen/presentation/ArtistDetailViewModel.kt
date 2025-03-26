package com.example.feature_artist_detail_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_artist_detail_screen.domain.entity.ArtistDetailEntity
import com.example.feature_artist_detail_screen.domain.entity.TopTracksEntity
import com.example.feature_artist_detail_screen.domain.usecase.ArtistDetailGetArtistTopTracksUseCase
import com.example.feature_artist_detail_screen.domain.usecase.ArtistDetailGetArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistUseCase: ArtistDetailGetArtistUseCase,
    private val getArtistTopTracksUseCase: ArtistDetailGetArtistTopTracksUseCase
) :
    ViewModel() {
    private val artistDetailIntent = MutableSharedFlow<ArtistDetailIntent>()

    private val _artistDetailState = MutableStateFlow(ArtistDetailState())
    val artistDetailState: StateFlow<ArtistDetailState> = _artistDetailState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            artistDetailIntent.collectLatest { event ->
                when (event) {
                    is ArtistDetailIntent.GetArtistResponse -> getArtist(event.artistId)
                    is ArtistDetailIntent.GetArtistTopTracksResponse -> getArtistTopTracks(event.artistId)
                }
            }
        }
    }


    private fun getArtist(artistId: String) {
        _artistDetailState.value =
            _artistDetailState.value.copy(status = ArtistDetailStatus.Loading)
        viewModelScope.launch {
            getArtistUseCase.execute(artistId).onSuccess {
                if (it is ArtistDetailEntity) {
                    _artistDetailState.value = _artistDetailState.value.copy(
                        status = ArtistDetailStatus.Success,
                        artist = it
                    )
                } else {
                    _artistDetailState.value = _artistDetailState.value.copy(
                        status = ArtistDetailStatus.Success,
                        message = "No data"
                    )
                }
            }.onFailure {
                _artistDetailState.value = _artistDetailState.value.copy(
                    status = ArtistDetailStatus.Success,
                    message = it.message
                )
            }
        }
    }

    private fun getArtistTopTracks(artistId: String) {
        _artistDetailState.value =
            _artistDetailState.value.copy(status = ArtistDetailStatus.Loading)
        viewModelScope.launch {
            getArtistTopTracksUseCase.execute(artistId).onSuccess {
                if (it is TopTracksEntity) {
                    _artistDetailState.value = _artistDetailState.value.copy(
                        status = ArtistDetailStatus.Success,
                        topTracks = it
                    )
                } else {
                    _artistDetailState.value = _artistDetailState.value.copy(
                        status = ArtistDetailStatus.Success,
                        message = "No data"
                    )
                }
            }.onFailure {
                _artistDetailState.value = _artistDetailState.value.copy(
                    status = ArtistDetailStatus.Success,
                    message = it.message
                )
            }
        }
    }

    fun sendIntent(intent: ArtistDetailIntent) {
        viewModelScope.launch {
            artistDetailIntent.emit(intent)
        }
    }
}