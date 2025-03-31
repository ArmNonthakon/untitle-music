package com.example.feature_home_screen.domain.mapper

import com.example.core.data.model.search.PlaylistSearchResponse
import com.example.feature_home_screen.domain.entity.playlist.PlaylistEntity
import com.example.feature_home_screen.domain.entity.playlist.PlaylistSeveralEntity
import javax.inject.Inject

class HomeScreenPlaylistMapper @Inject constructor() {
    fun map(playlistResponse: PlaylistSearchResponse): PlaylistSeveralEntity {
        val playlist = playlistResponse.items.map {
            it?.let { it1 ->
                PlaylistEntity(
                    description = it1.description, id = it.id, images = it.images, name = it.name, owner = it.owner, uri = it.uri
                )
            }
        }
        return PlaylistSeveralEntity(playlist)
    }
}