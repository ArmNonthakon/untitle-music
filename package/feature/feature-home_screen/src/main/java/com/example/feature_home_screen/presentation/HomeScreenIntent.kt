package com.example.feature_home_screen.presentation

sealed interface HomeScreenIntent{
    data object GetUserDetail : HomeScreenIntent
    data object GetAlbumNewReleases : HomeScreenIntent
    data object GetSeveralTracks : HomeScreenIntent
    data object GetYourTopTracks : HomeScreenIntent
    data object GetYourTopArtists : HomeScreenIntent
}