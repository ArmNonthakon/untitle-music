package com.example.feature_home_screen.domain.mapper

import com.example.core.data.model.track.TrackResponse
import com.example.feature_home_screen.domain.entity.track.TrackEntity
import com.example.feature_home_screen.domain.entity.user.UserEntity
import javax.inject.Inject

class HomeScreenTrackMapper @Inject constructor() {
    fun map(trackResponse: TrackResponse): TrackEntity {
        return TrackEntity(
            album = trackResponse.album,
            artists = trackResponse.artists,
            discNumber = trackResponse.discNumber,
            durationMs = trackResponse.durationMs,
            externalIds = trackResponse.externalIds,
            externalUrls = trackResponse.externalUrls,
            href = trackResponse.href,
            id = trackResponse.id,
            isPlayable = trackResponse.isPlayable,
            name = trackResponse.name,
            popularity = trackResponse.popularity,
            trackNumber = trackResponse.trackNumber,
            type = trackResponse.type,
            uri = trackResponse.uri
        )
    }
}