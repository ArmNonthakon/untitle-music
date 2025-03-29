package com.example.feature_playlist_screen.presentation

sealed interface PlaylistIntent {
    data class GetPlaylist(val playlistId : String) : PlaylistIntent
}