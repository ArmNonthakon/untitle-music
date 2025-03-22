package com.example.feature_album_detail_screen.ui

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
import com.example.core.data.model.getAlbumResponse.ArtistAlbumResponse
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppViewModel
import com.example.feature_album_detail_screen.presentation.AlbumDetailIntent
import com.example.feature_album_detail_screen.presentation.AlbumDetailState
import com.example.feature_album_detail_screen.presentation.AlbumDetailViewModel


@Composable
fun AlbumDetailProvider(
    viewModel: AlbumDetailViewModel = hiltViewModel(),
    appViewModel: AppViewModel,
    navController: NavController,
    albumId : String
) {
    val state = viewModel.albumDetailState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.sendIntent(AlbumDetailIntent.GetAlbumResponse(albumId))

    }

    AlbumDetailScreen(appViewModel = appViewModel, navController = navController, state = state,)
}

@Composable
fun AlbumDetailScreen(
    appViewModel: AppViewModel,
    navController: NavController,
    state: AlbumDetailState
) {
    Column(Modifier.padding(top = 10.dp)) {
        BuildAlbumDetailHeader(
            appViewModel = appViewModel,
            navController = navController,
            state = state
        )
        Spacer(Modifier.size(15.dp))
        BuildAlbumDetailContent(appViewModel = appViewModel, state = state)
    }
}


@Composable
fun BuildAlbumDetailHeader(
    appViewModel: AppViewModel,
    navController: NavController,
    state: AlbumDetailState
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    navController.navigate("Home")
                }
        )
    }
}

@Composable
fun BuildAlbumDetailContent(appViewModel: AppViewModel, state: AlbumDetailState) {
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
                model = state.data?.images?.get(0)?.url,
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
            Box(
                Modifier
                    .size(355.dp)
                    .background(Color.White)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = state.data?.images?.get(0)?.url,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row (Modifier
                .fillMaxWidth().padding(horizontal = 10.dp)
                ,horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                state.data?.let {
                    Text(
                        it.name,
                        style = TextStyle(color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.W800),
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp).weight(9f),
                        maxLines = 2
                    )
                }
                Box(Modifier.weight(1f)){
                    Box(
                        Modifier
                            .clip(shape = CircleShape)
                            .background(Color(69, 183, 221))
                            .size(30.dp).align(alignment = Alignment.BottomCenter)

                    ) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "play",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clickable {
                                    state.data?.uri?.let { AppIntent.PlaySong(uri = it) }
                                        ?.let { appViewModel.sendIntent(it) }
                                }
                        )
                    }
                }

            }
            Row (Modifier.padding(horizontal = 10.dp)){
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        Modifier
                            .clip(shape = CircleShape)
                            .background(Color.White)
                            .size(30.dp)
                    ){
                        if(state.artist != null){
                            AsyncImage(
                                model = state.artist.images[0].url,
                                contentDescription = "artist",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    state.data?.artists?.get(0)
                        ?.let {
                            Text(
                                it.name,
                                style = TextStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.W600)
                            )
                        }
                }
            }


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(131, 131, 131))
            )
            Row (Modifier
                .fillMaxWidth()
                .background(Color(35, 35, 35))
                .padding(horizontal = 10.dp, vertical = 5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                Text("#Title", style = TextStyle(color = Color.White, fontSize = 15.sp , fontWeight = FontWeight.W600))
                Icon(painter = painterResource(id = R.drawable.schedule), contentDescription = "schedule", modifier = Modifier.size(20.dp), tint = Color.White)
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(131, 131, 131))
            )
            state.data?.tracks?.items?.map {
                TracksDetail(songName = it.name, artist = it.artists,duration = it.durationMs,it.uri,appViewModel = appViewModel)
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(131, 131, 131))
                )
            }
            Spacer(Modifier.size(20.dp))


        }
    }

}

@SuppressLint("DefaultLocale")
@Composable
fun TracksDetail(songName: String, artist: List<ArtistAlbumResponse>,duration: Int,uri : String,appViewModel: AppViewModel) {
    val minutes = duration / 60000
    val seconds = (duration % 60000) / 1000
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color(35, 35, 35)).clickable {
                appViewModel.sendIntent(AppIntent.PlaySong(uri))
            }
    ) {
        Row (Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Column(
                Modifier.padding(vertical = 10.dp, horizontal = 10.dp).weight(10f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    songName,
                    style = TextStyle(fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.W600),
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
                        style = TextStyle(fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.W600),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Box (Modifier.weight(2f).padding(end = 10.dp)){
                Text(
                    String.format("%2d:%02d", minutes, seconds),
                    style = TextStyle(fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.W600),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(alignment = Alignment.CenterEnd)
                )
            }

        }
    }
}