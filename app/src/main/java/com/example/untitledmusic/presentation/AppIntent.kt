package com.example.untitledmusic.presentation

sealed interface AppIntent {
    data object GetPlayBackState : AppIntent
}