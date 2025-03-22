package com.example.core.data.service

import com.example.core.data.model.album.albumNewReleases.AlbumsNewReleaseResponse
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.artist.artistYourTop.ArtistYourTopResponse
import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.core.data.model.player.playBackState.PlayBackStateResponse
import com.example.core.data.model.track.trackSeveral.TrackSeveralResponse
import com.example.core.data.model.track.trackYourTopResponse.TrackYourTopResponse
import com.example.core.data.model.user.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("v1/me")
    suspend fun getUserDetail(): Response<UserResponse>

    @GET("v1/browse/new-releases")
    suspend fun getAlbumNewRelease(
        @Query("limit") limit: Int = 10
    ): Response<AlbumsNewReleaseResponse>

    @GET("v1/tracks")
    suspend fun getSeveralTracks(
        @Query("market") market: String = "TH",
        @Query("ids") ids: String = "7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B"
    ): Response<TrackSeveralResponse>

    @GET("v1/me/top/tracks")
    suspend fun getYourTopTracks(
        @Query("time_range") timeRange : String = "long_term",
        @Query("limit") limit: Int = 10
    ): Response<TrackYourTopResponse>

    @GET("v1/me/top/artists")
    suspend fun getYourTopArtists(
        @Query("time_range") timeRange : String = "long_term",
        @Query("limit") limit: Int = 10
    ): Response<ArtistYourTopResponse>

    @GET("v1/me/player")
    suspend fun getPlayBackState(): Response<PlayBackStateResponse>

    @GET("v1/albums/{id}")
    fun getAlbum(@Path("id") albumId: String?): Call<GetAlbumResponse>

    @GET("v1/artists/{id}")
    fun getArtist(
        @Path("id") artistId: String,
    ): Call<ArtistResponse>


}