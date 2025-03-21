package com.example.feature_album_detail_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.usecase.AlbumDetailCallBack
import com.example.feature_album_detail_screen.domain.usecase.AlbumDetailGetAlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(private val useCase: AlbumDetailGetAlbumUseCase) :
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
                }
            }
        }
    }

    private fun getAlbum(albumId: String) {
        _albumDetailState.value = _albumDetailState.value.copy(status = AlbumDetailStatus.Loading)
        viewModelScope.launch {
            useCase.execute(albumId, object : AlbumDetailCallBack {
                override fun onSuccess(entity: AlbumDetailEntity?) {
                    _albumDetailState.value = _albumDetailState.value.copy(
                        status = AlbumDetailStatus.Success,
                        data = entity
                    )
                }

                override fun onError(errorMessage: String) {
                    _albumDetailState.value = _albumDetailState.value.copy(
                        status = AlbumDetailStatus.Success,
                        message = errorMessage
                    )
                }

            })
        }

    }

    fun sendIntent(intent: AlbumDetailIntent) {
        viewModelScope.launch {
            albumDetailIntent.emit(intent)
        }
    }
}