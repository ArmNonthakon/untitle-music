package com.example.feature_search_screen.domain.entity

data class SearchEntity(
    val tracks : List<TrackEntity>,
    val artists: List<ArtistEntity>,
    val albums : List<AlbumEntity>,
    val playlists : List<PlaylistEntity>
)
