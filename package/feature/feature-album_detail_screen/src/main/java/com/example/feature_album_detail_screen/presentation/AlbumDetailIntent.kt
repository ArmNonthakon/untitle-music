package com.example.feature_album_detail_screen.presentation

sealed interface AlbumDetailIntent{
    data class GetAlbumResponse(val albumId: String) : AlbumDetailIntent
}