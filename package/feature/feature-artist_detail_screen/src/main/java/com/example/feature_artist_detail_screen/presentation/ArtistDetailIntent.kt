package com.example.feature_artist_detail_screen.presentation

sealed interface ArtistDetailIntent{
    data class GetArtistResponse(val artistId: String) : ArtistDetailIntent
    data class GetArtistTopTracksResponse(val artistId: String) : ArtistDetailIntent
}