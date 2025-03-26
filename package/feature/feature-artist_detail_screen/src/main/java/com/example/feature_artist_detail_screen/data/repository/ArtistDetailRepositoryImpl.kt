package com.example.feature_artist_detail_screen.data.repository

import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.artist.artistTopTracks.ArtistTopTracksResponse
import com.example.core.data.model.track.TrackResponse
import com.example.core.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject

class ArtistDetailRepositoryImpl @Inject constructor(private val service: ApiService) : ArtistDetailRepository{
    override suspend fun getArtist(artistId: String): Response<ArtistResponse> {
        return service.getArtist(artistId)
    }

    override suspend fun getArtistTopTracks(artistId: String): Response<ArtistTopTracksResponse> {
        return  service.getArtistTopTracks(artistId)
    }
}