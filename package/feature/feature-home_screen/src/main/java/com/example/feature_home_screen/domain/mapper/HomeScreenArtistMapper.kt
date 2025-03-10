package com.example.feature_home_screen.domain.mapper

import com.example.core.data.model.artist.ArtistResponse
import com.example.feature_home_screen.domain.entity.artist.ArtistEntity
import javax.inject.Inject

class HomeScreenArtistMapper @Inject constructor() {
    fun map(artistResponse: ArtistResponse): ArtistEntity {
        return ArtistEntity(
            followers = artistResponse.followers,
            href = artistResponse.href,
            id = artistResponse.id,
            image = artistResponse.images,
            name = artistResponse.name,
            type = artistResponse.type,
            uri = artistResponse.uri
        )
    }
}