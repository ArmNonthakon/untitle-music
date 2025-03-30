package com.example.core.presentation

sealed interface AppIntent {
    data object GetPlayBackState : AppIntent
    data class PlaySong(val uri: String) : AppIntent
    data object ResumePauseSong : AppIntent
    data object NextSong : AppIntent
    data object PreviousSong : AppIntent
    
}