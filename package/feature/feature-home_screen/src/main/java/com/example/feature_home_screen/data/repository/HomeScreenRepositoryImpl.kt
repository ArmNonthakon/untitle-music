package com.example.feature_home_screen.data.repository

import com.example.core.data.model.album.albumNewReleases.AlbumsNewReleaseResponse
import com.example.core.data.model.artist.artistYourTop.ArtistYourTopResponse
import com.example.core.data.model.track.trackSeveral.TrackSeveralResponse
import com.example.core.data.model.track.trackYourTopResponse.TrackYourTopResponse
import com.example.core.data.model.user.UserResponse
import com.example.core.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class HomeScreenRepositoryImpl @Inject constructor (private val service: ApiService) : HomeScreenRepository{
    override suspend fun getUserDetail(): Response<UserResponse> {
        return service.getUserDetail();
    }

    override suspend fun getAlbumNewRelease(): Response<AlbumsNewReleaseResponse> {
        return service.getAlbumNewRelease()
    }

    override suspend fun getSeveralTracks(): Response<TrackSeveralResponse> {
        return service.getSeveralTracks()
    }

    override suspend fun getYourTopTracks(): Response<TrackYourTopResponse> {
        return service.getYourTopTracks()
    }

    override suspend fun getYourTopArtists(): Response<ArtistYourTopResponse> {
        return service.getYourTopArtists()
    }
}