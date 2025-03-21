package com.example.feature_album_detail_screen.domain.mapper

import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import javax.inject.Inject

class AlbumDetailMapper @Inject constructor(){
    fun mapper(album: GetAlbumResponse) : AlbumDetailEntity{
        return AlbumDetailEntity(
            totalTracks = album.totalTracks,
            id = album.id,
            images = album.images,
            name = album.name,
            uri = album.uri,
            artists = album.artists,
            tracks = album.tracks
        )
    }
}