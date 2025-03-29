package com.example.feature_playlist_screen.domain.mapper

import com.example.core.data.model.playlist.PlaylistResponse
import com.example.feature_playlist_screen.domain.entity.PlaylistEntity
import javax.inject.Inject

class PlaylistMapper @Inject constructor(){
    fun mapper(playlistResponse: PlaylistResponse): PlaylistEntity{
        return PlaylistEntity(
            description = playlistResponse.description,
            id = playlistResponse.id,
            images = playlistResponse.images,
            name = playlistResponse.name,
            owner = playlistResponse.owner,
            tracks = playlistResponse.tracks,
            uri = playlistResponse.uri
        )
    }
}