package com.example.feature_home_screen.domain.mapper

import com.example.core.data.model.album.AlbumResponse
import com.example.feature_home_screen.domain.entity.album.AlbumEntity
import javax.inject.Inject

class HomeScreenAlbumMapper @Inject constructor() {
    fun map(album: AlbumResponse): AlbumEntity {
        return AlbumEntity(
            totalTracks = album.totalTracks,
            href = album.href,
            id = album.id,
            images = album.images,
            name = album.name,
            uri = album.uri
        )
    }
}