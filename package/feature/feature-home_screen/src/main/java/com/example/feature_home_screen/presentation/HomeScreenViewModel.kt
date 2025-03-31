package com.example.feature_home_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_home_screen.domain.entity.album.albumNewReleases.AlbumNewReleasesEntity
import com.example.feature_home_screen.domain.entity.artist.artistYourTop.ArtistYourTopEntity
import com.example.feature_home_screen.domain.entity.playlist.PlaylistSeveralEntity
import com.example.feature_home_screen.domain.entity.track.trackSeveral.TrackSeveralEntity
import com.example.feature_home_screen.domain.entity.track.trackYourTop.TrackYourTopEntity
import com.example.feature_home_screen.domain.entity.user.UserEntity
import com.example.feature_home_screen.domain.usecase.HomeScreenGetAlbumNewReleasesUserCase
import com.example.feature_home_screen.domain.usecase.HomeScreenGetSeveralPlaylist
import com.example.feature_home_screen.domain.usecase.HomeScreenGetSeveralTrackUseCase
import com.example.feature_home_screen.domain.usecase.HomeScreenGetUserUserCase
import com.example.feature_home_screen.domain.usecase.HomeScreenGetYourTopArtists
import com.example.feature_home_screen.domain.usecase.HomeScreenGetYourTopTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getUserUserCase: HomeScreenGetUserUserCase,
    private val getAlbumNewReleasesUserCase: HomeScreenGetAlbumNewReleasesUserCase,
    private val getSeveralTrackUseCase: HomeScreenGetSeveralTrackUseCase,
    private val getYourTopTracks: HomeScreenGetYourTopTracks,
    private val getYourTopArtists: HomeScreenGetYourTopArtists,
    private val getSeveralPlaylist: HomeScreenGetSeveralPlaylist,
) : ViewModel() {
    private val homeScreenIntent = MutableSharedFlow<HomeScreenIntent>()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState
    private val errorNoDataMessage = "No data"
    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            homeScreenIntent.collectLatest { event ->
                when (event) {
                    HomeScreenIntent.GetUserDetail -> getUserEvent()
                    HomeScreenIntent.GetAlbumNewReleases -> getAlbumNewReleaseEvent()
                    HomeScreenIntent.GetSeveralTracks -> getSeveralTrackEvent()
                    HomeScreenIntent.GetYourTopArtists -> getYourTopArtists()
                    HomeScreenIntent.GetYourTopTracks -> getYourTopTracks()
                    HomeScreenIntent.GetSeveralPlaylist -> getSeveralPlaylistEvent()
                }
            }
        }
    }

    private fun getUserEvent() {
        _homeScreenState.value = _homeScreenState.value.copy(status = HomeScreenStatus.Loading)
        viewModelScope.launch {
            val result = getUserUserCase.execute()
            if (result.isSuccess && result.getOrNull() is UserEntity) {
                val entity = result.getOrNull() as UserEntity
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Success,
                    user = entity
                )
            } else {
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Failed,
                    message = result.exceptionOrNull()?.message ?: "Unknown error"
                )
            }
        }
    }

    private fun getAlbumNewReleaseEvent() {
        _homeScreenState.value = _homeScreenState.value.copy(status = HomeScreenStatus.Loading)
        viewModelScope.launch {
            getAlbumNewReleasesUserCase.execute().onSuccess {
                if (it is AlbumNewReleasesEntity) {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Success,
                        data = _homeScreenState.value.data.copy(albumNewRelease = it)
                    )
                } else {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Failed,
                        message = "No data"
                    )
                }
            }.onFailure {
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    private fun getSeveralTrackEvent() {
        _homeScreenState.value = _homeScreenState.value.copy(status = HomeScreenStatus.Loading)
        viewModelScope.launch {
            getSeveralTrackUseCase.execute(q = "genre:t-pop").onSuccess {
                if (it is TrackSeveralEntity) {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Success,
                        data = _homeScreenState.value.data.copy(trackSeveral = it)
                    )
                } else {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Failed,
                        message = errorNoDataMessage
                    )
                }
            }.onFailure {
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    private fun getSeveralPlaylistEvent() {
        _homeScreenState.value = _homeScreenState.value.copy(status = HomeScreenStatus.Loading)
        viewModelScope.launch {
            getSeveralPlaylist.execute(q = "เพลง").onSuccess {
                if (it is PlaylistSeveralEntity) {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Success,
                        data = _homeScreenState.value.data.copy(playlistSeveral = it)
                    )
                } else {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Failed,
                        message = errorNoDataMessage
                    )
                }
            }.onFailure {
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    private fun getYourTopTracks() {
        _homeScreenState.value = _homeScreenState.value.copy(status = HomeScreenStatus.Loading)
        viewModelScope.launch {
            getYourTopTracks.execute().onSuccess {
                if (it is TrackYourTopEntity) {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Success,
                        data = _homeScreenState.value.data.copy(yourTopTracks = it)
                    )
                } else {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Failed,
                        message = errorNoDataMessage
                    )
                }
            }.onFailure {
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    private fun getYourTopArtists() {
        _homeScreenState.value = _homeScreenState.value.copy(status = HomeScreenStatus.Loading)
        viewModelScope.launch {
            getYourTopArtists.execute().onSuccess {
                if (it is ArtistYourTopEntity) {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Success,
                        data = _homeScreenState.value.data.copy(yourTopArtists = it)
                    )
                } else {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        status = HomeScreenStatus.Failed,
                        message = errorNoDataMessage
                    )
                }
            }.onFailure {
                _homeScreenState.value = _homeScreenState.value.copy(
                    status = HomeScreenStatus.Failed,
                    message = it.message.toString()
                )
            }
        }
    }

    fun sendIntent(intent: HomeScreenIntent) {
        viewModelScope.launch {
            homeScreenIntent.emit(intent)
        }
    }
}
