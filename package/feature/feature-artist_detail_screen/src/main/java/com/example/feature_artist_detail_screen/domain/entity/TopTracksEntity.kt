package com.example.feature_artist_detail_screen.domain.entity

import com.example.core.data.model.ExternalIds
import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse

data class TopTracksEntity(
    val items : List<TrackEntity>
)



