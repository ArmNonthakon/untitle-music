package com.example.feature_artist_detail_screen.ui

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import coil3.compose.AsyncImage
import com.example.core.R
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppViewModel
import com.example.feature_artist_detail_screen.presentation.ArtistDetailIntent
import com.example.feature_artist_detail_screen.presentation.ArtistDetailState
import com.example.feature_artist_detail_screen.presentation.ArtistDetailStatus
import com.example.feature_artist_detail_screen.presentation.ArtistDetailViewModel


@Composable
fun ArtistDetailProvider(
    viewModel: ArtistDetailViewModel = hiltViewModel(),
    appViewModel: AppViewModel,
    navController: NavController,
    artistId: String,
) {
    val state = viewModel.artistDetailState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.sendIntent(ArtistDetailIntent.GetArtistResponse(artistId))
        viewModel.sendIntent(ArtistDetailIntent.GetArtistTopTracksResponse(artistId))
    }

    ArtistDetailScreen(appViewModel = appViewModel, navController = navController, state = state)
}

@Composable
fun ArtistDetailScreen(
    appViewModel: AppViewModel,
    navController: NavController,
    state: ArtistDetailState,
) {
    Column(Modifier.padding(top = 10.dp)) {
        BuildAlbumDetailHeader(
            navController = navController,
        )
        Spacer(Modifier.size(15.dp))
        BuildAlbumDetailContent(appViewModel = appViewModel, state = state)
    }
}


@Composable
fun BuildAlbumDetailHeader(
    navController: NavController,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun BuildAlbumDetailContent(appViewModel: AppViewModel, state: ArtistDetailState) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .height(500.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            AsyncImage(
                model = state.artist?.images?.get(0)?.url,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha = 0.6f)
            )
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .background(Color(35, 35, 35, 170))
        ) {
            ArtistDetailSection(state, appViewModel)


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(131, 131, 131))
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(35, 35, 35))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "#Title",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.schedule),
                    contentDescription = "schedule",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }

            ArtistTrackList(state, appViewModel)

        }
    }

}

@Composable
fun ArtistDetailSection(state: ArtistDetailState, appViewModel: AppViewModel) {
    Column {

        //Image
        Box(
            Modifier
                .size(355.dp)
                .background(Color.White)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = state.artist?.images?.get(0)?.url,
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        //Detail
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.status == ArtistDetailStatus.Success) {
                state.artist?.let {
                    Text(
                        it.name,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.W800
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .weight(9f),
                        maxLines = 2
                    )
                }
            } else {
                Box(Modifier
                    .weight(9f)
                    .padding(top = 10.dp, bottom = 10.dp)) {
                    Box(
                        Modifier
                            .width(200.dp)
                            .height(height = 26.dp)
                            .background(Color.White)
                    )
                }
            }

            Box(Modifier.weight(1f)) {
                Box(
                    Modifier
                        .clip(shape = CircleShape)
                        .background(Color(69, 183, 221))
                        .size(30.dp)
                        .align(alignment = Alignment.BottomCenter)

                ) {
                    Icon(
                        Icons.Filled.PlayArrow,
                        contentDescription = "play",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                state.artist?.uri?.let { AppIntent.PlaySong(uri = it) }
                                    ?.let { appViewModel.sendIntent(it) }
                            }
                    )
                }
            }

        }
        Row(Modifier.padding(horizontal = 10.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if(state.status == ArtistDetailStatus.Success){
                    Text(
                        "${"%,d".format(state.artist?.follower?.total)} Followers",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            letterSpacing = 2.5.sp
                        )
                    )
                }else{
                    Box(
                        Modifier
                            .width(150.dp)
                            .height(height = 14.dp)
                            .background(Color.White)
                    )
                }

            }
        }
    }
}

@Composable
fun ArtistTrackList(state: ArtistDetailState, appViewModel: AppViewModel) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(131, 131, 131))
        )
        if (state.status == ArtistDetailStatus.Loading) {
            for (i in 1..3) {
                TracksDetailskeleton()
            }
        } else if (state.status == ArtistDetailStatus.Success) {
            state.topTracks?.items?.map {
                TracksDetail(
                    songName = it.name,
                    artist = it.artists,
                    duration = it.durationMs,
                    it.uri,
                    appViewModel = appViewModel
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(131, 131, 131))
                )
            }
        }
        Spacer(Modifier.size(20.dp))
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TracksDetail(
    songName: String,
    artist: List<ArtistResponse>,
    duration: Int,
    uri: String,
    appViewModel: AppViewModel
) {
    val minutes = duration / 60000
    val seconds = (duration % 60000) / 1000
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color(35, 35, 35))
            .clickable {
                appViewModel.sendIntent(AppIntent.PlaySong(uri))
            }
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .weight(10f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    songName,
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.size(5.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(Icons.Filled.Person, contentDescription = "Artist", tint = Color.White)
                    Text(
                        artist.joinToString(", ") { it.name },
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W600
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Box(Modifier
                .weight(2f)
                .padding(end = 10.dp)) {
                Text(
                    String.format("%2d:%02d", minutes, seconds),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(alignment = Alignment.CenterEnd)
                )
            }

        }
    }
}

@Composable
fun TracksDetailskeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color(35, 35, 35))
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .weight(10f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier
                        .size(width = 200.dp, height = 15.dp)
                        .background(Color.White)
                )
                Spacer(Modifier.size(5.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(Icons.Filled.Person, contentDescription = "Artist", tint = Color.White)
                    Box(
                        Modifier
                            .size(width = 100.dp, height = 12.dp)
                            .background(Color.White)
                    )
                }
            }
            Box(Modifier
                .weight(2f)
                .padding(end = 10.dp)) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(height = 14.dp)
                        .background(Color.White)
                )
            }

        }
    }
}