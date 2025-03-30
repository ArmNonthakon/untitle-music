package com.example.untitledmusic.ui

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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.core.R
import com.example.core.data.model.track.TrackResponse
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppStatus
import com.example.core.presentation.AppViewModel
import com.example.core.storage.SecureSharedPreferences
import com.example.untitledmusic.routes.AppNavHost
import com.example.untitledmusic.ui.theme.SpotmusicTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                            AppNavHost(navController,viewModel)
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
                Column (Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Top){
                    Text(
                        track?.name ?: "Song Name",
                        color = Color.White,
                        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.W700),
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
            Spacer(Modifier.size(5.dp))
            Row(Modifier.weight(5f)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "back",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable(interactionSource = null, indication = null){
                                    viewModel.sendIntent(AppIntent.PreviousSong)
                                }
                        )
                        Image(
                            painter = painterResource(id = if (!isPlay) R.drawable.play else R.drawable.pause),
                            contentDescription = "play",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(interactionSource = null, indication = null) {
                                    viewModel.sendIntent(AppIntent.ResumePauseSong)
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.next),
                            contentDescription = "next",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable(interactionSource = null, indication = null) {
                                    scope.launch {
                                        viewModel.sendIntent(AppIntent.NextSong)
                                    }
                                }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    track?.let {
                        ProgressBar(
                            currentProgress = progress,
                            duration = it.durationMs,
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun ProgressBar(
    currentProgress: Double,
    duration: Int = 0,
) {
    val progressPercent = (currentProgress / duration.toDouble())
    val width = 200
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


