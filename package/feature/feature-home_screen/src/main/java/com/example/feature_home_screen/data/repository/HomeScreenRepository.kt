package com.example.feature_home_screen.data.repository

import com.example.core.data.model.album.albumNewReleases.AlbumsNewReleaseResponse
import com.example.core.data.model.artist.artistYourTop.ArtistYourTopResponse
import com.example.core.data.model.search.SearchResponse
import com.example.core.data.model.track.trackSeveral.TrackSeveralResponse
import com.example.core.data.model.track.trackYourTopResponse.YourTopTrackResponse
import com.example.core.data.model.user.UserResponse
import retrofit2.Response


interface HomeScreenRepository {
    suspend fun getUserDetail(): Response<UserResponse>

    suspend fun getAlbumNewRelease(): Response<AlbumsNewReleaseResponse>

    suspend fun getSeveralTracks(): Response<TrackSeveralResponse>

    suspend fun getYourTopTracks(): Response<YourTopTrackResponse>

    suspend fun getYourTopArtists(): Response<ArtistYourTopResponse>

    suspend fun searchItems(q: String) : Response<SearchResponse>
}