package com.example.feature_search_screen.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.core.R
import com.example.core.data.model.Image
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.presentation.AppIntent
import com.example.core.presentation.AppViewModel
import com.example.core.storage.SecureSharedPreferences
import com.example.feature_search_screen.domain.entity.AlbumEntity
import com.example.feature_search_screen.domain.entity.ArtistEntity
import com.example.feature_search_screen.domain.entity.PlaylistEntity
import com.example.feature_search_screen.presentation.SearchIntent
import com.example.feature_search_screen.presentation.SearchState
import com.example.feature_search_screen.presentation.SearchStatus
import com.example.feature_search_screen.presentation.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import androidx.compose.foundation.layout.Box as Box1

@Composable
fun SearchProvider(
    viewModel: SearchViewModel = hiltViewModel(),
    appViewModel: AppViewModel,
    navController: NavController
) {
    val secureStorage = SecureSharedPreferences.getInstance(context = LocalContext.current)
    val state = viewModel.searchScreenState.collectAsState().value
    SearchScreen(
        viewModel = viewModel, appViewModel = appViewModel, navController = navController, state,secureStorage = secureStorage
    )
}


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    appViewModel: AppViewModel,
    navController: NavController,
    state: SearchState,
    secureStorage : SharedPreferences
) {
    Column {
        BuildSearchScreenHeader(viewModel, navController,secureStorage)
        when (state.status) {
            SearchStatus.Idle -> {
                Text("")
            }

            SearchStatus.Loading -> {
                BuildSearchScreenContent(appViewModel, navController, state)
            }

            SearchStatus.Success -> {
                BuildSearchScreenContent(appViewModel, navController, state)
            }

            SearchStatus.Failed -> {
                Text(state.message, style = TextStyle(color = Color.White))
            }
        }
    }

}

@SuppressLint("CommitPrefEdits")
@Composable
fun BuildSearchScreenHeader(
    viewModel: SearchViewModel,
    navController: NavController,
    secureStorage: SharedPreferences
) {
    LaunchedEffect (Unit){
        Log.d("SecureStorage", "Before removal: " + secureStorage.getString("cache_search_value", "Not found"))
    }
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
                .clickable(interactionSource = null, indication = null) {
                    navController.popBackStack()
                }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            SearchBar(viewModel, secureStorage)
        }
    }
}


@Composable
fun BuildSearchScreenContent(
    appViewModel: AppViewModel, navController: NavController, state: SearchState
) {
    Column(
        Modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TrackSectionContent(appViewModel, state)
        AlbumSectionContent(navController, state)
        ArtistSectionContent(navController, state)
        PlayListSectionContent(navController, state)

        Spacer(Modifier.size(15.dp))
    }
}

@Composable
fun TrackSectionContent(appViewModel: AppViewModel, state: SearchState) {
    Column(Modifier.padding(top = 10.dp, bottom = 5.dp)) {
        Text(
            "Song",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        when (state.status) {
            SearchStatus.Idle -> {
                Text("")
            }

            SearchStatus.Loading -> {
                for (i in 1..5) {
                    TracksDetail("", listOf(), "", null, appViewModel)
                }
            }

            SearchStatus.Success -> {
                val tracks = state.data?.tracks
                tracks?.map {
                    TracksDetail(
                        songName = it.name,
                        artist = it.artists,
                        appViewModel = appViewModel,
                        uri = it.uri,
                        image = it.album.images[0]
                    )
                }
            }

            SearchStatus.Failed -> {
                Text("", style = TextStyle(color = Color.White, fontSize = 18.sp))
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
    }
}

@Composable
fun PlayListSectionContent(navController: NavController, state: SearchState) {
    Column {
        Text(
            "Playlist",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            ), horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            when (state.status) {
                SearchStatus.Idle -> {
                    Text("")
                }

                SearchStatus.Loading -> {
                    for (i in 1..3) {
                        AlbumBox(navController, null)
                    }

                }

                SearchStatus.Success -> {
                    val playlists = state.data?.playlists
                    if (playlists == null) {
                        PlaylistBox(navController, null)
                        PlaylistBox(navController, null)
                    }
                    playlists?.map {
                        PlaylistBox(navController, it)
                    }
                }

                SearchStatus.Failed -> {
                    Text("")
                }
            }
        }
        Spacer(Modifier.size(30.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
    }
}

@Composable
fun AlbumSectionContent(navController: NavController, state: SearchState) {
    Column {
        Text(
            "Album",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            ), horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            when (state.status) {
                SearchStatus.Idle -> {
                    Text("")
                }

                SearchStatus.Loading -> {
                    for (i in 1..3) {
                        AlbumBox(navController, null)
                    }

                }

                SearchStatus.Success -> {
                    val albums = state.data?.albums
                    albums?.map {
                        AlbumBox(navController, it)
                    }
                }

                SearchStatus.Failed -> {
                    Text("")
                }
            }
        }
        Spacer(Modifier.size(30.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
    }
}

@Composable
fun ArtistSectionContent(navController: NavController, state: SearchState) {
    Column {
        Text(
            "Artist",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            ), horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            when (state.status) {
                SearchStatus.Idle -> {
                    Text("")
                }

                SearchStatus.Loading -> {
                    for (i in 1..3) {
                        ArtistBox(navController, null)
                    }

                }

                SearchStatus.Success -> {
                    val artists = state.data?.artists
                    artists?.map {
                        ArtistBox(navController, it)
                    }
                }

                SearchStatus.Failed -> {
                    Text("")
                }
            }
        }
        Spacer(Modifier.size(30.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
    }
}

@Composable
fun PlaylistBox(navController: NavController, playlist: PlaylistEntity?) {
    Column(Modifier.width(200.dp)) {
        Box(
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        ) {
            if (playlist != null) {
                AsyncImage(model = playlist.images.getOrNull(0)?.url ?: "",
                    contentDescription = "back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("Playlist/${playlist.id}")
                        })
            }
        }
        Spacer(Modifier.size(5.dp))
        if (playlist != null) {
            Text(
                playlist.name,
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W700, color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 3.dp)
            )
        } else {
            Box(
                Modifier
                    .size(width = 100.dp, height = 16.dp)
                    .background(Color.White)
            )
        }

    }
}


@Composable
fun AlbumBox(navController: NavController, album: AlbumEntity?) {
    Column(Modifier.width(200.dp)) {
        Box(
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        ) {
            if (album != null) {
                AsyncImage(model = album.images[0].url,
                    contentDescription = "back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("Album/${album.id}")
                        })
            }
        }
        Spacer(Modifier.size(5.dp))
        if (album != null) {
            Text(
                album.name,
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W700, color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 3.dp)
            )
        } else {
            Box(
                Modifier
                    .size(width = 100.dp, height = 16.dp)
                    .background(Color.White)
            )
        }

    }
}

@Composable
fun ArtistBox(navController: NavController, artist: ArtistEntity?) {
    Column {
        Box(
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        ) {
            if (artist != null) {
                AsyncImage(
                    model = artist.image.getOrNull(0)?.url ?: "",
                    contentDescription = "back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate("Artist/${artist.id}")
                        }
                )
            }
        }
        Spacer(Modifier.size(5.dp))
        if (artist != null) {
            Text(
                artist.name,
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.W700, color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 3.dp)
            )
        } else {
            Box(
                Modifier
                    .size(width = 100.dp, height = 16.dp)
                    .background(Color.White)
            )
        }
    }
}

@Composable
fun TracksDetail(
    songName: String,
    artist: List<ArtistResponse>,
    uri: String,
    image: Image? = null,
    appViewModel: AppViewModel
) {
    Column {
        Box1(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
        Box1(modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(Color(35, 35, 35))
            .clickable {
                appViewModel.sendIntent(AppIntent.PlaySong(uri))
            }) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box1(
                    Modifier
                        .size(50.dp)
                        .background(Color.White)
                ) {
                    AsyncImage(
                        model = image?.url,
                        contentDescription = "back",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    if (songName == "") {
                        Box(
                            Modifier
                                .size(width = 100.dp, height = 14.dp)
                                .background(Color.White)
                        )
                    } else {
                        Text(
                            songName, style = TextStyle(
                                fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.W600
                            ), maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                    }


                    Spacer(Modifier.size(5.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = "Artist", tint = Color.White)
                        if (artist.isEmpty()) {
                            Box(
                                Modifier
                                    .size(width = 100.dp, height = 12.dp)
                                    .background(Color.White)
                            )
                        } else {
                            Text(
                                artist.joinToString(", ") { it.name }, style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.W600
                                ), maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                        }


                    }
                }
            }
        }

    }
}

@SuppressLint("CommitPrefEdits")
@OptIn(FlowPreview::class)
@Composable
fun SearchBar(viewModel: SearchViewModel,secureStorage : SharedPreferences) {
    val focusRequester = remember { FocusRequester() }
    var searchText by remember { mutableStateOf(TextFieldValue(
        text = "",)) }
    val searchFlow = remember { MutableStateFlow("") }
    LaunchedEffect(Unit) {
        val cacheSearchText = secureStorage.getString("cache_search_value", "")
        if(cacheSearchText != null && cacheSearchText != ""){
            val textFieldValue = TextFieldValue(
                text = cacheSearchText,
                selection = TextRange(cacheSearchText.length)
            )
            searchText = textFieldValue
            viewModel.sendIntent(SearchIntent.SearchItems(cacheSearchText))
        }
        focusRequester.requestFocus()
        searchFlow
            .debounce(300)
            .collect { query ->
                if (query.isNotEmpty()) {
                    viewModel.sendIntent(SearchIntent.SearchItems(query))
                }
            }
    }
    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
            searchFlow.value = it.text
            if(it.text.isEmpty()){
                viewModel.sendIntent(SearchIntent.InitialState)
                secureStorage.edit().apply {
                    remove("cache_search_value")
                    apply()
                }
            }
            secureStorage.edit().apply {
                putString("cache_search_value", it.text)
                apply()
            }
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.W500,
            letterSpacing = 1.5.sp
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(78, 78, 78),
            focusedContainerColor = Color(78, 78, 78),
            focusedIndicatorColor = Color(69, 183, 221),
            focusedTextColor = Color.White,
            cursorColor = Color.White
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Black,
                modifier = Modifier.size(30.dp),
            )
        },
        placeholder = {
            Text(
                "What do you want to listen?", style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 1.5.sp
                )
            )
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .height(height = 50.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )
}