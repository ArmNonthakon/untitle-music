package com.example.untitledmusic.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.presentation.AppViewModel
import com.example.feature_album_detail_screen.ui.AlbumDetailProvider
import com.example.feature_artist_detail_screen.ui.ArtistDetailProvider
import com.example.feature_home_screen.ui.HomeScreenProvider
import com.example.feature_player_screen.ui.PlayerProvider
import com.example.feature_playlist_screen.ui.PlaylistProvider
import com.example.feature_search_screen.ui.SearchProvider


@Composable
fun AppNavHost(navController: NavHostController, viewModel: AppViewModel) {
    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeScreenProvider(
                appViewModel = viewModel,
                navController = navController
            )
        }
        composable(route = "Album/{albumId}") {
            val albumId = it.arguments?.getString("albumId")
            if (albumId != null) {
                AlbumDetailProvider(
                    appViewModel = viewModel,
                    navController = navController,
                    albumId = albumId
                )
            }
        }
        composable(route = "Artist/{artistId}") {
            val artistId = it.arguments?.getString("artistId")
            if (artistId != null) {
                ArtistDetailProvider(
                    appViewModel = viewModel,
                    navController = navController,
                    artistId = artistId
                )
            }
        }
        composable(route = "Playlist/{playlistId}") {
            val playlistId = it.arguments?.getString("playlistId")
            if (playlistId != null) {
                PlaylistProvider(
                    appViewModel = viewModel,
                    navController = navController,
                    playlistId = playlistId
                )
            }
        }
        composable(route = "Player") {
            PlayerProvider(
                appViewModel = viewModel,
                navController = navController
            )
        }
        composable(route = "Search") {
            SearchProvider(
                appViewModel = viewModel,
                navController = navController
            )
        }
    }
}