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
import com.example.feature_home_screen.domain.entity.album.albumNewReleases.AlbumNewReleasesEntity
import com.example.feature_home_screen.domain.entity.track.TrackEntity
import com.example.feature_home_screen.domain.entity.track.trackSeveral.TrackSeveralEntity
import com.example.feature_home_screen.domain.entity.user.UserEntity
import com.example.feature_home_screen.presentation.HomeScreenIntent
import com.example.feature_home_screen.presentation.HomeScreenState
import com.example.feature_home_screen.presentation.HomeScreenStatus
import com.example.feature_home_screen.presentation.HomeScreenViewModel

@Composable
fun HomeScreenProvider(viewModel: HomeScreenViewModel = hiltViewModel(),appViewModel: AppViewModel,navController: NavController) {
    val state = viewModel.homeScreenState.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.sendIntent(HomeScreenIntent.GetUserDetail)
        viewModel.sendIntent(HomeScreenIntent.GetAlbumNewReleases)
        viewModel.sendIntent(HomeScreenIntent.GetSeveralTracks)
        viewModel.sendIntent(HomeScreenIntent.GetYourTopTracks)
        viewModel.sendIntent(HomeScreenIntent.GetYourTopArtists)
    }

    HomeScreen(state,appViewModel,navController)
}


@Composable
fun HomeScreen(state: HomeScreenState,appViewModel: AppViewModel,navController: NavController) {
    val configuration = LocalConfiguration.current
    val widthScreen = configuration.screenWidthDp.dp

    Box(Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            BuildHomeScreenHeader(widthScreen = widthScreen, user = state.user, navController = navController)
            BuildHomeScreenContent(state,appViewModel,navController)
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
                        76,
                        75,
                        75,
                        255
                    ), strokeWidth = 8.dp, strokeCap = StrokeCap.Round, trackColor = Color(
                        50,
                        50,
                        50,
                        255
                    )
                )
            }
        }
    }


}


@Composable
fun BuildHomeScreenHeader(widthScreen: Dp, user: UserEntity?,navController: NavController) {
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
        Box(
            modifier = Modifier
                .size(width = widthScreen - (widthScreen / 3), height = 40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(78, 78, 78))
                .padding(horizontal = 15.dp).clickable {
                    navController.navigate("Search")
                }
        ) {
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
fun BuildHomeScreenContent(state: HomeScreenState,appViewModel: AppViewModel,navController: NavController) {
    val isTopArtist = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (state.status == HomeScreenStatus.Loading) {
            Row(
                modifier = Modifier.horizontalScroll(
                    state = rememberScrollState(),
                )
            ) {
                for (i in 1..3) {
                    AlbumBox(navController = navController)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
        }
        RecommendAlbum(state,navController)

        SongBoxGroup(topic = "For you", state)

        Row(Modifier.padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Button(
                modifier = Modifier.height(36.dp),
                onClick = {
                    isTopArtist.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTopArtist.value) Color(
                        69,
                        183,
                        221
                    ) else Color(84, 89, 90)
                )
            ) {
                Text(
                    "Artists",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    )
                )
            }
            Button(
                modifier = Modifier.height(36.dp),
                onClick = {
                    isTopArtist.value = false
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isTopArtist.value) Color(
                        69,
                        183,
                        221
                    ) else Color(84, 89, 90)
                )
            ) {
                Text(
                    "Music",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W600
                    )
                )
            }
        }
        if (isTopArtist.value) {
            RankingGroup(topic = "Your Top 10 Artist", state, isTopArtist = true, appViewModel = appViewModel)
        } else {
            RankingGroup(topic = "Your Top 10 Music", state, isTopArtist = false,appViewModel = appViewModel)
        }


    }
}
//Component

@Composable
fun RecommendAlbum(state: HomeScreenState,navController: NavController) {
    Column {
        Text(
            "New Releases",
            style = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W600)
        )
        Spacer(Modifier.size(10.dp))
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            )
        ) {
            if (state.data.albumNewRelease == null) {
                AlbumBox(navController = navController)
                Spacer(modifier = Modifier.padding(5.dp))
            } else {
                for (i in state.data.albumNewRelease.items) {
                    AlbumBox(i.images[0],navController,i.id)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
            }
            when (state.status) {
                HomeScreenStatus.Idle -> {
                    Text("Idle")
                }

                HomeScreenStatus.Loading -> {
                    Text("Loading...")
                }

                HomeScreenStatus.Success -> {
                    if (state.data.albumNewRelease is AlbumNewReleasesEntity) {
                        for (i in state.data.albumNewRelease.items) {
                            AlbumBox(i.images[0],navController,i.id)
                            Spacer(modifier = Modifier.padding(5.dp))
                        }
                    }
                }

                HomeScreenStatus.Failed -> {
                    Box(
                        modifier = Modifier
                            .size(width = 200.dp, height = 200.dp)
                            .background(Color.White)
                    ) {
                        Text(state.message, Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }
}

@Composable
fun SongBoxGroup(topic: String, state: HomeScreenState? = null) {
    Column {
        Text(
            topic,
            style = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W600)
        )
        Spacer(Modifier.padding(5.dp))
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            )
        ) {
            when (state?.status) {
                HomeScreenStatus.Idle -> {
                    Text("Idle")
                }

                HomeScreenStatus.Loading -> {
                    Text("Loading...")
                }

                HomeScreenStatus.Success -> {
                    if (state.data.trackSeveral is TrackSeveralEntity) {
                        for (i in state.data.trackSeveral.track) {
                            SongBox(track = i)
                            Spacer(modifier = Modifier.padding(5.dp))
                        }
                    }
                }

                HomeScreenStatus.Failed -> {
                    Box(
                        modifier = Modifier
                            .size(95.dp)
                            .background(Color.White)
                    ) {
                        Text(state.message, Modifier.align(Alignment.Center))

                    }
                }

                null -> {
                    Box(
                        modifier = Modifier
                            .size(95.dp)
                            .background(Color.White)
                    ) {
                        Text("No data", Modifier.align(Alignment.Center))
                    }
                }
            }


        }
    }

}

@Composable
fun RankingGroup(topic: String, state: HomeScreenState, isTopArtist: Boolean,appViewModel: AppViewModel) {
    Column {
        Text(
            topic,
            style = TextStyle(fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.W600)
        )
        Spacer(Modifier.padding(5.dp))
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            when (state.status) {
                HomeScreenStatus.Idle -> Text("Idle")
                HomeScreenStatus.Loading -> Text("Loading")
                HomeScreenStatus.Success -> {
                    if (isTopArtist) {
                        state.data.yourTopArtists?.items?.mapIndexed { index, item ->
                            RankingCard(
                                number = index + 1,
                                image = item.image[0].url,
                                title = item.name,
                                subTitle = "${"%,d".format(item.followers.total)} Followers",
                                isFirst = index == 0,
                                uri = item.uri,
                                appViewModel = appViewModel
                            )
                        }
                    } else {
                        state.data.yourTopTracks?.items?.mapIndexed { index, item ->
                            RankingCard(
                                number = index + 1,
                                image = item.album.images[0].url,
                                title = item.name,
                                subTitle = item.artists.joinToString(", ") { it.name },
                                isFirst = index == 0,
                                uri = item.uri,
                                appViewModel = appViewModel
                            )
                        }
                    }
                }

                HomeScreenStatus.Failed -> {
                    Box(
                        modifier = Modifier
                            .size(95.dp)
                            .background(Color.White)
                    ) {
                        Text(state.message, Modifier.align(Alignment.Center))
                    }
                }
            }

        }
    }
}


//Widget
@Composable
fun AlbumBox(image: Image? = null,navController: NavController,albumId : String? = null) {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 200.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
    ) {
        AsyncImage(
            model = image?.url,
            contentDescription = "back",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().clickable {
                navController.navigate("Album/$albumId")
            }
        )
    }
}

@Composable
fun SongBox(track: TrackEntity) {
    Column() {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
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
            style = TextStyle(fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.W600)
        )
        Text(
            track.artists[0].name,
            style = TextStyle(fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.W500),
        )
    }
}

@Composable
fun RankingCard(number: Int, image: String, title: String, subTitle: String, isFirst: Boolean,uri : String,appViewModel: AppViewModel) {
    Box(modifier = Modifier.padding(horizontal = if (isFirst) 0.dp else 10.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 0.5.dp,
                    color = Color(131, 131, 131),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(if (isFirst) Color(94, 166, 190, 228) else Color(0, 0, 0, 0))
        ) {
            Row(
                Modifier
                    .padding(top = 8.dp, start = 5.dp, end = 10.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (Modifier.weight(9f)){
                    Text(
                        "$number",
                        style = TextStyle(
                            fontWeight = FontWeight.W600,
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.width(22.dp)
                    )
                    Spacer(Modifier.size(5.dp))
                    Box(
                        Modifier
                            .size(if (isFirst) 95.dp else 85.dp).clip(shape = RoundedCornerShape(5.dp))
                    ) {
                        AsyncImage(
                            model = image,
                            contentDescription = "back",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(Modifier.size(10.dp))
                    Column {
                        Text(
                            title,
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
                            subTitle,
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
                Box (Modifier.fillMaxHeight().weight(1f)){
                    Box(
                        Modifier
                            .clip(shape = CircleShape)
                            .background(Color(69, 183, 221))
                            .size(30.dp).align(Alignment.TopEnd)
                    ) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "play",
                            modifier = Modifier.align(Alignment.Center).clickable {
                                appViewModel.sendIntent(AppIntent.PlaySong(uri = uri))
                            }
                        )
                    }
                }
            }
        }
    }
}