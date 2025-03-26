package com.example.feature_artist_detail_screen.data.repository

import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.artist.artistTopTracks.ArtistTopTracksResponse
import com.example.core.data.model.track.TrackResponse
import retrofit2.Response

sealed interface ArtistDetailRepository {
    suspend fun getArtist(artistId: String): Response<ArtistResponse>

    suspend fun getArtistTopTracks(artistId: String): Response<ArtistTopTracksResponse>
}