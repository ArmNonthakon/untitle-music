package com.example.feature_album_detail_screen.data.repository

import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import retrofit2.Call
import retrofit2.Response

interface AlbumDetailRepository {
    suspend fun getAlbum(albumId: String): Response<GetAlbumResponse>
    suspend fun getArtist(artistId: String): Response<ArtistResponse>
}