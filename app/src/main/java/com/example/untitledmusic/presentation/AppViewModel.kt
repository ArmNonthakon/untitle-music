package com.example.untitledmusic.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.untitledmusic.domain.entity.PlayBackStateEntity
import com.example.untitledmusic.domain.usecase.AppGetPlayBackStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val getPlayBackStateUseCase: AppGetPlayBackStateUseCase) : ViewModel(){
    private val appIntent = MutableSharedFlow<AppIntent>()

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            appIntent.collectLatest { event ->
                when (event) {
                   AppIntent.GetPlayBackState -> getAlbumNewReleaseEvent()
                }
            }
        }
    }
    private fun getAlbumNewReleaseEvent() {
        _appState.value = _appState.value.copy(status = AppStatus.Loading)
        viewModelScope.launch {
            getPlayBackStateUseCase.execute().onSuccess {
                if (it is PlayBackStateEntity) {
                    _appState.value = _appState.value.copy(
                        status = AppStatus.Success,
                        player = it
                    )
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

    fun sendIntent(intent: AppIntent) {
        viewModelScope.launch {
            appIntent.emit(intent)
        }
    }
}