package com.example.feature_artist_detail_screen.domain.mapper

import com.example.core.data.model.artist.ArtistResponse
import com.example.feature_artist_detail_screen.domain.entity.ArtistDetailEntity
import javax.inject.Inject

class ArtistDetailMapper @Inject constructor(){
    fun mapper(artist: ArtistResponse) : ArtistDetailEntity {
        return ArtistDetailEntity(id = artist.id, name = artist.name ,images = artist.images,follower = artist.followers, uri = artist.uri)
    }
}