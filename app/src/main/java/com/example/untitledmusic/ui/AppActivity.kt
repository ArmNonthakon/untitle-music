package com.example.untitledmusic.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.core.R
import com.example.core.data.model.track.TrackResponse
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppStatus
import com.example.core.presentation.AppViewModel
import com.example.core.storage.SecureSharedPreferences
import com.example.feature_album_detail_screen.ui.AlbumDetailProvider
import com.example.feature_artist_detail_screen.ui.ArtistDetailProvider
import com.example.feature_artist_detail_screen.ui.ArtistDetailScreen
import com.example.feature_home_screen.ui.HomeScreenProvider
import com.example.feature_player_screen.ui.PlayerProvider
import com.example.feature_playlist_screen.ui.PlaylistProvider
import com.example.feature_search_screen.ui.SearchProvider
import com.example.untitledmusic.ui.theme.SpotmusicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: AppViewModel = hiltViewModel()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            println(currentRoute)

            LaunchedEffect(Unit) {
                viewModel.sendIntent(AppIntent.GetPlayBackState)
            }

            val state = viewModel.appState.collectAsState().value

            SpotmusicTheme {
                Scaffold(bottomBar = {
                    if (state.status == AppStatus.Success && currentRoute != "Player") {
                        state.player?.progressMs?.let {
                            MusicBottomAppBar(
                                state.player!!.item,
                                progress = it.toDouble(),
                                isPlay = state.playerState.isPlaying,
                                viewModel = viewModel,
                                navController = navController
                            )

                        }
                    }
                }) { innerPadding ->
                    Box(
                        Modifier
                            .padding(innerPadding)
                            .background(color = Color(35, 35, 35))
                    ) {
                        Box(
                            Modifier
                                .padding(10.dp)
                                .fillMaxSize()
                        ) {
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
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val secureStorage = SecureSharedPreferences.getInstance(context = this)
        secureStorage.edit().remove("cache_search_value").apply()
    }
}

@Composable
fun MusicBottomAppBar(
    track: TrackResponse? = null,
    progress: Double = 0.0,
    isPlay: Boolean = false,
    viewModel: AppViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    BottomAppBar(Modifier, containerColor = Color.Black) {
        Row(
            Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.weight(5f)) {
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(color = Color.White)
                ) {
                    track?.let {
                        AsyncImage(
                            model = it.album.images[0].url,
                            contentDescription = "player image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clickable {
                                navController.navigate("Player")
                            }
                        )
                    }
                }
                Spacer(Modifier.width(5.dp))
                Column {
                    Text(
                        track?.name ?: "Song Name",
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        track?.artists?.joinToString(", ") { it.name } ?: "Artist",
                        color = Color.White,
                        style = TextStyle(fontSize = 12.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(Modifier.weight(5f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "back",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    viewModel.sendIntent(AppIntent.PreviousSong)
                                }
                        )
                        Image(
                            painter = painterResource(id = if (!isPlay) R.drawable.play else R.drawable.pause),
                            contentDescription = "play",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    viewModel.sendIntent(AppIntent.ResumePauseSong)
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.next),
                            contentDescription = "next",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    scope.launch {
                                        viewModel.sendIntent(AppIntent.NextSong)
                                    }
                                }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    track?.let {
                        ProgressBar(
                            progress = progress,
                            duration = it.durationMs,
                            isPlay = isPlay,
                            viewModel
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun ProgressBar(
    progress: Double,
    duration: Int = 0,
    isPlay: Boolean = false,
    viewModel: AppViewModel
) {
    val currentProgress = remember { mutableDoubleStateOf(progress) }
    val progressPercent = (currentProgress.doubleValue / duration.toDouble())
    val width = 200

    LaunchedEffect(Unit) {
        if (isPlay) {
            while (currentProgress.doubleValue < duration) {
                delay(duration = Duration.ofSeconds(1))
                currentProgress.value += 1000
            }
            viewModel.sendIntent(AppIntent.GetPlayBackState)
        }
    }

    Box(
        Modifier
            .size(width = width.dp, height = 3.dp)
            .background(color = Color.Gray)
    ) {
        Box(
            Modifier
                .width(width = (width * progressPercent).dp)
                .height(3.dp)
                .background(
                    color = Color(
                        69,
                        183,
                        221
                    )
                )
        )
    }
}


