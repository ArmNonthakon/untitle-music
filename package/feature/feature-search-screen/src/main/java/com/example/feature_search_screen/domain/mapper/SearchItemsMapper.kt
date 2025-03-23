package com.example.feature_search_screen.domain.mapper

import com.example.core.data.model.search.SearchResponse
import com.example.feature_search_screen.domain.entity.AlbumEntity
import com.example.feature_search_screen.domain.entity.ArtistEntity
import com.example.feature_search_screen.domain.entity.SearchEntity
import com.example.feature_search_screen.domain.entity.TrackEntity
import javax.inject.Inject

class SearchItemsMapper @Inject constructor() {
    fun mapper(searchResponse: SearchResponse): SearchEntity {
        val tracks = searchResponse.tracks.items.map {
            TrackEntity(
                album = it.album,
                artists = it.artists,
                id = it.id,
                isPlayable = it.isPlayable,
                name = it.name,
                uri = it.uri,
            )
        }
        val albums = searchResponse.albums.items.map {
            AlbumEntity(
                totalTracks = it.totalTracks,
                href = it.href,
                id = it.id,
                images = it.images,
                name = it.name,
                uri = it.uri
            )
        }
        val artists = searchResponse.artists.items.map {
            ArtistEntity(
                followers = it.followers,
                href = it.href,
                id = it.id,
                image = it.images,
                name = it.name,
                type = it.type,
                uri = it.uri

            )
        }
        return SearchEntity(
            tracks = tracks, artists = artists, albums = albums
        )
    }
}