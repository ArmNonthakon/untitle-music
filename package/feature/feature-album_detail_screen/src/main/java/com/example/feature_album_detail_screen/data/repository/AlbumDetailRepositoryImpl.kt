package com.example.feature_album_detail_screen.data.repository

import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.core.data.service.ApiService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class AlbumDetailRepositoryImpl @Inject constructor(private val service: ApiService) : AlbumDetailRepository{
    override suspend fun getAlbum(albumId : String): Call<GetAlbumResponse> {
        return service.getAlbum(albumId);
    }

    override suspend fun getArtist(artistId: String): Response<ArtistResponse> {
        return service.getArtist(artistId);
    }
}