package com.example.feature_home_screen.presentation

import com.example.feature_home_screen.domain.entity.album.albumNewReleases.AlbumNewReleasesEntity
import com.example.feature_home_screen.domain.entity.artist.artistYourTop.ArtistYourTopEntity
import com.example.feature_home_screen.domain.entity.playlist.PlaylistEntity
import com.example.feature_home_screen.domain.entity.playlist.PlaylistSeveralEntity
import com.example.feature_home_screen.domain.entity.track.trackSeveral.TrackSeveralEntity
import com.example.feature_home_screen.domain.entity.track.trackYourTop.TrackYourTopEntity
import com.example.feature_home_screen.domain.entity.user.UserEntity

enum class HomeScreenStatus {
    Idle,Loading,Success,Failed
}

data class HomeScreenState(
    val status: HomeScreenStatus = HomeScreenStatus.Idle,
    val user : UserEntity? = null,
    val message: String = "",
    val data: HomeScreenData = HomeScreenData()
)

data class HomeScreenData(
    val albumNewRelease : AlbumNewReleasesEntity? = null,
    val trackSeveral : TrackSeveralEntity? = null,
    val playlistSeveral : PlaylistSeveralEntity? = null,
    val yourTopTracks: TrackYourTopEntity? = null ,
    val yourTopArtists: ArtistYourTopEntity? = null
)