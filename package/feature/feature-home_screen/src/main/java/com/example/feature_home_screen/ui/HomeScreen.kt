package com.example.feature_home_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.core.data.model.Image
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppViewModel
import com.example.core.storage.SecureSharedPreferences
import com.example.feature_home_screen.domain.entity.album.albumNewReleases.AlbumNewReleasesEntity
import com.example.feature_home_screen.domain.entity.artist.ArtistEntity
import com.example.feature_home_screen.domain.entity.playlist.PlaylistEntity
import com.example.feature_home_screen.domain.entity.playlist.PlaylistSeveralEntity
import com.example.feature_home_screen.domain.entity.track.TrackEntity
import com.example.feature_home_screen.domain.entity.track.trackSeveral.TrackSeveralEntity
import com.example.feature_home_screen.domain.entity.user.UserEntity
import com.example.feature_home_screen.presentation.HomeScreenIntent
import com.example.feature_home_screen.presentation.HomeScreenState
import com.example.feature_home_screen.presentation.HomeScreenStatus
import com.example.feature_home_screen.presentation.HomeScreenViewModel

@Composable
fun HomeScreenProvider(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    appViewModel: AppViewModel,
    navController: NavController
) {
    val state = viewModel.homeScreenState.collectAsState().value
    val secureStorage = SecureSharedPreferences.getInstance(context = LocalContext.current)
    LaunchedEffect(Unit) {
        secureStorage.edit().remove("cache_search_value").apply()
        viewModel.sendIntent(HomeScreenIntent.GetUserDetail)
        viewModel.sendIntent(HomeScreenIntent.GetAlbumNewReleases)
        viewModel.sendIntent(HomeScreenIntent.GetSeveralTracks)
        viewModel.sendIntent(HomeScreenIntent.GetYourTopTracks)
        viewModel.sendIntent(HomeScreenIntent.GetYourTopArtists)
        viewModel.sendIntent(HomeScreenIntent.GetSeveralPlaylist)
    }

    HomeScreen(state, appViewModel, navController)
}


@Composable
fun HomeScreen(state: HomeScreenState, appViewModel: AppViewModel, navController: NavController) {
    val configuration = LocalConfiguration.current
    val widthScreen = configuration.screenWidthDp.dp

    Box(Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            BuildHomeScreenHeader(
                widthScreen = widthScreen,
                user = state.user,
                navController = navController
            )
            if(state.status == HomeScreenStatus.Failed){
                Box (Modifier.fillMaxSize()){
                    Text(state.message, style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.W700, color = Color.White), modifier = Modifier.align(Alignment.Center))
                }
            }else{
                BuildHomeScreenContent(state, appViewModel, navController)
            }
        }
        if (state.status == HomeScreenStatus.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(35, 35, 35, 120))
            ) {
                CircularProgressIndicator(
                    Modifier
                        .align(Alignment.Center)
                        .zIndex(1f)
                        .size(60.dp), color = Color(
                        76, 75, 75, 255
                    ), strokeWidth = 8.dp, strokeCap = StrokeCap.Round, trackColor = Color(
                        50, 50, 50, 255
                    )
                )
            }
        }
    }


}


@Composable
fun BuildHomeScreenHeader(widthScreen: Dp, user: UserEntity?, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            if (user != null) {
                if (user.images.isNotEmpty()) {
                    AsyncImage(
                        model = user.images[0].url,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        Modifier
                            .background(Color(39, 176, 248, 255))
                            .fillMaxSize()
                    ) {
                        Text(
                            user.displayName[0].uppercase(),
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier
            .size(width = widthScreen - (widthScreen / 3), height = 40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(78, 78, 78)).clickable {
                navController.navigate("Search")
            }
            .padding(horizontal = 15.dp)) {
            Row(
                Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.padding(5.dp))
                Text(
                    "What do you want to listen ?",
                    modifier = Modifier,
                    color = Color.White,
                    style = TextStyle(fontSize = 15.sp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        Icon(
            Icons.Filled.Notifications,
            contentDescription = "Notifications",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun BuildHomeScreenContent(
    state: HomeScreenState,
    appViewModel: AppViewModel,
    navController: NavController
) {
    val isTopArtist = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {

        RecommendAlbum(state, navController)

        SongBoxGroup(topic = "For you",state = state, navController = navController)

        PlaylistBoxGroup(topic = "Recommend playlist", state = state, navController = navController)


        Row(Modifier.padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            if(state.status == HomeScreenStatus.Success){
                Button(
                    modifier = Modifier.height(36.dp), onClick = {
                        isTopArtist.value = true
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = if (isTopArtist.value) Color(
                            69, 183, 221
                        ) else Color(84, 89, 90)
                    )
                ) {
                    Text(
                        "Artists", style = TextStyle(
                            fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.W600
                        )
                    )
                }
                Button(
                    modifier = Modifier.height(36.dp), onClick = {
                        isTopArtist.value = false
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isTopArtist.value) Color(
                            69, 183, 221
                        ) else Color(84, 89, 90)
                    )
                ) {
                    Text(
                        "Music", style = TextStyle(
                            fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.W600
                        )
                    )
                }
            }else{
                Box(Modifier
                    .size(width = 100.dp, height = 36.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White))
                Box(Modifier
                    .size(width = 100.dp, height = 36.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White))
            }
        }
        if (isTopArtist.value) {
            RankingGroup(
                topic = "Your Top 10 Artist",
                state,
                isTopArtist = true,
                appViewModel = appViewModel,
                navController = navController
            )
        } else {
            RankingGroup(
                topic = "Your Top 10 Music",
                state,
                isTopArtist = false,
                appViewModel = appViewModel,
                navController = navController
            )
        }
    }
}
//Component

@Composable
fun RecommendAlbum(state: HomeScreenState, navController: NavController) {
    Column {
        if (state.status == HomeScreenStatus.Success && state.data.albumNewRelease is AlbumNewReleasesEntity) {
            Text(
                "New Releases",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
            )
            Spacer(Modifier.size(10.dp))
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in state.data.albumNewRelease.items) {
                    AlbumBox(i.images[0], navController, i.id)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        } else {
            Box(
                Modifier
                    .size(width = 100.dp, height = 18.dp)
                    .background(Color.White)
            )
            Spacer(Modifier.size(10.dp))
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in 1..3) {
                    AlbumBoxSkeleton()
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}

@Composable
fun SongBoxGroup(topic: String, state: HomeScreenState,navController: NavController) {
    Column {
        if (state.status == HomeScreenStatus.Success && state.data.trackSeveral is TrackSeveralEntity) {
            Text(
                topic,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
            )
            Spacer(Modifier.padding(5.dp))
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in state.data.trackSeveral.track) {
                    SongBox(track = i, navController = navController)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        } else {
            Box(Modifier
                .size(width = 100.dp, height = 18.dp)
                .background(Color.White))
            Spacer(Modifier.padding(5.dp))
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in 1..3) {
                    SongBoxSkeleton()
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}
@Composable
fun PlaylistBoxGroup(topic: String, state: HomeScreenState,navController: NavController) {
    Column {
        if (state.status == HomeScreenStatus.Success && state.data.playlistSeveral is PlaylistSeveralEntity) {
            Text(
                topic,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
            )
            Spacer(Modifier.padding(5.dp))
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in state.data.playlistSeveral.items) {
                    i?.let { PlaylistBox(playlist = it, navController = navController) }
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        } else {
            Box(Modifier
                .size(width = 100.dp, height = 18.dp)
                .background(Color.White))
            Spacer(Modifier.padding(5.dp))
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in 1..3) {
                    PlaylistSkeleton()
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}


@Composable
fun RankingGroup(
    topic: String,
    state: HomeScreenState,
    isTopArtist: Boolean,
    appViewModel: AppViewModel,
    navController: NavController
) {
    Column {
        if (state.status == HomeScreenStatus.Success) {
            Text(
                topic,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W600
                )
            )
            Spacer(Modifier.padding(5.dp))
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                if (isTopArtist) {
                    state.data.yourTopArtists?.items?.mapIndexed { index, item ->
                        ArtistRankingCard(
                            number = index + 1,
                            artist = item,
                            appViewModel = appViewModel,
                            navController = navController
                        )
                    }
                } else {
                    state.data.yourTopTracks?.items?.mapIndexed { index, item ->
                        TrackRankingCard(
                            number = index + 1,
                            track = item,
                            appViewModel = appViewModel,
                            navController = navController
                        )
                    }
                }
            }
        } else {
            Box(Modifier
                .size(width = 100.dp, height = 18.dp)
                .background(Color.White))
            Spacer(Modifier.padding(5.dp))
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                for (i in 1..3){
                    RankingCardSkeleton(i)
                }
            }
        }
    }
}


//Widget
@Composable
fun AlbumBox(image: Image? = null, navController: NavController, albumId: String? = null) {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
    ) {
        AsyncImage(model = image?.url,
            contentDescription = "back",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navController.navigate("Album/$albumId")
                })
    }
}

@Composable
fun AlbumBoxSkeleton() {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
    )
}

@Composable
fun SongBox(track: TrackEntity,navController: NavController) {
    Column (Modifier.width(150.dp)){
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White).clickable {
                    navController.navigate("Album/${track.album.id}")
                }
        ) {
            AsyncImage(
                model = track.album.images[1].url,
                contentDescription = "back",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.size(5.dp))
        Text(
            track.name,
            style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.W600),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            track.artists[0].name,
            style = TextStyle(fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.W500),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PlaylistBox(playlist: PlaylistEntity, navController: NavController) {
    Column (Modifier.width(150.dp)){
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White).clickable {
                    navController.navigate("Playlist/${playlist.id}")
                }
        ) {
            AsyncImage(
                model = playlist.images[0].url,
                contentDescription = "back",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.size(5.dp))
        Text(
            playlist.name,
            style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.W600),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SongBoxSkeleton() {
    Column {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        )
        Spacer(Modifier.size(5.dp))
        Box(
            Modifier
                .size(width = 100.dp, height = 16.dp)
                .background(Color.White)
        )
        Spacer(Modifier.size(5.dp))
        Box(
            Modifier
                .size(width = 40.dp, height = 14.dp)
                .background(Color.White)
        )
    }
}

@Composable
fun PlaylistSkeleton() {
    Column {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        )
        Spacer(Modifier.size(5.dp))
        Box(
            Modifier
                .size(width = 100.dp, height = 16.dp)
                .background(Color.White)
        )
    }
}


@Composable
fun ArtistRankingCard(
    number: Int,
    artist: ArtistEntity,
    appViewModel: AppViewModel,
    navController: NavController
) {
    Box(modifier = Modifier.padding(horizontal = if (number == 1) 0.dp else 10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 0.5.dp, color = Color(131, 131, 131), shape = RoundedCornerShape(10.dp)
                )
                .background(if (number == 1) Color(94, 166, 190, 228) else Color(0, 0, 0, 0))
                .clickable {
                    navController.navigate("Artist/${artist.id}")
                }
        ) {
            Row(
                Modifier
                    .padding(top = 8.dp, start = 5.dp, end = 10.dp, bottom = 8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.weight(9f)) {
                    Text(
                        "$number", style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ), modifier = Modifier.width(22.dp)
                    )
                    Spacer(Modifier.size(5.dp))
                    Box(
                        Modifier
                            .size(if (number == 1) 95.dp else 85.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                    ) {
                        AsyncImage(
                            model = artist.image[0].url,
                            contentDescription = "back",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(Modifier.size(10.dp))
                    Column {
                        Text(
                            artist.name,
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
                                fontSize = 18.sp,
                                color = Color.White,
                                letterSpacing = 1.2.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.size(7.dp))
                        Text(
                            "${"%,d".format(artist.followers.total)} Followers",
                            style = TextStyle(
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp,
                                color = Color.White,
                                letterSpacing = 1.6.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Box(
                        Modifier
                            .clip(shape = CircleShape)
                            .background(Color(69, 183, 221))
                            .size(30.dp)
                            .align(alignment = Alignment.TopEnd).clickable {
                                appViewModel.sendIntent(AppIntent.PlaySong(uri = artist.uri))
                            }

                    ) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "play",
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TrackRankingCard(
    number: Int,
    track: TrackEntity,
    appViewModel: AppViewModel,
    navController: NavController
) {
    Box(modifier = Modifier.padding(horizontal = if (number == 1) 0.dp else 10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 0.5.dp, color = Color(131, 131, 131), shape = RoundedCornerShape(10.dp)
                )
                .background(if (number == 1) Color(94, 166, 190, 228) else Color(0, 0, 0, 0))
                .clickable {
                    navController.navigate("Album/${track.album.id}")
                }
        ) {
            Row(
                Modifier
                    .padding(top = 8.dp, start = 5.dp, end = 10.dp, bottom = 8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.weight(9f)) {
                    Text(
                        "$number", style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ), modifier = Modifier.width(22.dp)
                    )
                    Spacer(Modifier.size(5.dp))
                    Box(
                        Modifier
                            .size(if (number == 1) 95.dp else 85.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                    ) {
                        AsyncImage(
                            model = track.album.images[0].url,
                            contentDescription = "back",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(Modifier.size(10.dp))
                    Column {
                        Text(
                            track.name,
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
                                fontSize = 18.sp,
                                color = Color.White,
                                letterSpacing = 1.2.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.size(7.dp))
                        Text(
                            track.artists.joinToString(", ") { it.name },
                            style = TextStyle(
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp,
                                color = Color.White,
                                letterSpacing = 1.6.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Box(
                        Modifier
                            .clip(shape = CircleShape)
                            .background(Color(69, 183, 221))
                            .size(30.dp)
                            .align(Alignment.TopEnd).clickable {
                                appViewModel.sendIntent(AppIntent.PlaySong(uri = track.uri))
                            }
                    ) {
                        Icon(Icons.Filled.PlayArrow,
                            contentDescription = "play",
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RankingCardSkeleton(
    number: Int,
) {
    Box(modifier = Modifier.padding(horizontal = if (number == 1) 0.dp else 10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 0.5.dp, color = Color(131, 131, 131), shape = RoundedCornerShape(10.dp)
                )
                .background(Color.White)
        ) {
            Row(
                Modifier
                    .padding(top = 8.dp, start = 5.dp, end = 10.dp, bottom = 8.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.weight(9f)) {
                    Spacer(Modifier.size(5.dp))
                    Box(
                        Modifier
                            .size(if (number == 1) 95.dp else 85.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(Color.White)
                    )
                    Spacer(Modifier.size(10.dp))
                    Column {
                        Box(Modifier
                            .size(width = 50.dp, height = 18.dp)
                            .background(Color.White))
                        Spacer(Modifier.size(7.dp))
                        Box(Modifier
                            .size(width = 100.dp, height = 14.dp)
                            .background(Color.White))
                    }
                }
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Box(
                        Modifier
                            .clip(shape = CircleShape)
                            .background(Color.White)
                            .size(30.dp)
                            .align(Alignment.TopEnd)
                    ) {

                    }
                }
            }
        }
    }
}