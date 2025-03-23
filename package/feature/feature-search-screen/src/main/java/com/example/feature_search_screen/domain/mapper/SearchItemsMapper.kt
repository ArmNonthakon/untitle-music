package com.example.feature_search_screen.domain.mapper

import com.example.core.data.model.search.SearchResponse
import com.example.core.data.model.search.Tracks
import com.example.feature_search_screen.domain.entity.AlbumEntity
import com.example.feature_search_screen.domain.entity.ArtistEntity
import com.example.feature_search_screen.domain.entity.PlaylistEntity
import com.example.feature_search_screen.domain.entity.SearchEntity
import com.example.feature_search_screen.domain.entity.TrackEntity
import javax.inject.Inject
import kotlin.reflect.typeOf

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
        var playlist: List<PlaylistEntity> = listOf()
        for(i in searchResponse.playlists.items){
            if(i != null){
                playlist = playlist.plus(
                    PlaylistEntity(
                        id = i.id,
                        images = i.images,
                        name = i.name,
                        snapshotId = i.snapshotId,
                        tracks = i.tracks,
                        uri = i.uri
                    )
                )
            }
        }

        return SearchEntity(
            tracks = tracks, artists = artists, albums = albums, playlists = playlist
        )
    }
}