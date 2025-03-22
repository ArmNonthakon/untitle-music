package com.example.feature_album_detail_screen.domain.mapper

import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.entity.ArtistDetailEntity
import javax.inject.Inject

class ArtistDetailMapper @Inject constructor(){
    fun mapper(artist: ArtistResponse) : ArtistDetailEntity {
        return ArtistDetailEntity(id = artist.id, images = artist.images)
    }
}