package com.example.feature_artist_detail_screen.domain.mapper

import com.example.core.data.model.track.TrackResponse
import com.example.feature_artist_detail_screen.domain.entity.TrackEntity
import javax.inject.Inject

class TopTracksMapper @Inject constructor() {
    fun mapper(trackResponse: TrackResponse): TrackEntity {
        return TrackEntity(
            album = trackResponse.album,
            artists = trackResponse.artists,
            id = trackResponse.id,
            name = trackResponse.name,
            uri = trackResponse.uri,
            durationMs = trackResponse.durationMs
        )
    }
}