package com.example.feature_player_screen.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.core.R
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppState
import com.example.core.presentation.AppViewModel


@Composable
fun PlayerProvider(appViewModel: AppViewModel, navController: NavController) {
    val state = appViewModel.appState.collectAsState().value
    LaunchedEffect(Unit) {
        appViewModel.sendIntent(AppIntent.GetPlayBackState)
    }
    PlayerScreen(appViewModel, navController, state)
}


@Composable
fun PlayerScreen(appViewModel: AppViewModel, navController: NavController, state: AppState) {

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Header
        BuildPlayerScreenHeader(navController)

        Spacer(Modifier.size(20.dp))

        BuildPlayerScreenTracksPicture(state)

        Spacer(Modifier.size(20.dp))

        BuildPlayerScreenTracksDetail(state)

        Spacer(Modifier.size(15.dp))

        BuildPlayerScreenPlayer(appViewModel, state)

    }
}


@Composable
fun BuildPlayerScreenHeader(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(25.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun BuildPlayerScreenTracksPicture(state: AppState) {
    val heightScreen = LocalConfiguration.current.screenHeightDp
    Box(
        Modifier
            .background(Color.White)
            .size((heightScreen / 1.7).dp)
    ) {
        AsyncImage(
            model = state.player?.item?.album?.images?.get(0)?.url,
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha = 0.5f)
        )
        Box(
            Modifier
                .background(Color.Black)
                .size((heightScreen / 2).dp)
                .align(Alignment.Center)
        ) {
            AsyncImage(
                model = state.player?.item?.album?.images?.get(0)?.url,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BuildPlayerScreenTracksDetail(state: AppState) {
    Column {
        state.player?.item?.let {
            Text(
                it.name,
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.White
                )
            )
        }

        Spacer(Modifier.size(10.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            state.player?.item?.artists?.let { artist ->
                Text(
                    artist.joinToString(", ") { it.name },
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600
                    )
                )
            }
        }
    }
}

@Composable
fun BuildPlayerScreenPlayer(viewModel: AppViewModel, state: AppState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back",
                modifier = Modifier
                    .size(37.dp)
                    .clickable {
                        viewModel.sendIntent(AppIntent.PreviousSong)
                    }
            )
            Image(
                painter = painterResource(id = if (!state.playerState.isPlaying) R.drawable.play else R.drawable.pause),
                contentDescription = "play",
                modifier = Modifier
                    .size(39.dp)
                    .clickable {
                        viewModel.sendIntent(AppIntent.ResumePauseSong)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.next),
                contentDescription = "next",
                modifier = Modifier
                    .size(37.dp)
                    .clickable {
                        viewModel.sendIntent(AppIntent.NextSong)
                    }
            )
        }
        Spacer(Modifier.size(25.dp))
        state.player?.let {
            it.durationMs?.let { it1 ->
                ProgressBar(
                    currentProgress = it.progressMs.toDouble(),
                    duration = it1
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ProgressBar(
    currentProgress: Double,
    duration: Int = 0,
) {
    val progressPercent = (currentProgress / duration.toDouble())
    val width = LocalConfiguration.current.screenWidthDp - 40
    val minutesDuration = duration / 60000
    val secondsDuration = (duration % 60000) / 1000


    Column {
        Box(
            Modifier
                .size(width = width.dp, height = 5.dp)
                .background(color = Color.Gray)
        ) {
            Box(
                Modifier
                    .width(width = (width * progressPercent).dp)
                    .height(5.dp)
                    .background(color = Color(69, 183, 221))
            )
        }
        Spacer(Modifier.size(10.dp))
        Row(Modifier.width(width.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                String.format(
                    "%02d:%02d",
                    (currentProgress.toInt() / 60000),
                    (currentProgress.toInt() % 60000) / 1000
                ),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                String.format("%02d:%02d", minutesDuration, secondsDuration),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
