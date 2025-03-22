package com.example.feature_search_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.core.R
import com.example.core.presentation.AppViewModel
import androidx.compose.foundation.layout.Box as Box1


@Composable
fun SearchScreen(appViewModel: AppViewModel,navController: NavController) {
    Column {
        BuildSearchScreenHeader(navController)
        BuildSearchScreenContent(appViewModel,navController)
    }

}

@Composable
fun BuildSearchScreenHeader(navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .size(25.dp)
                .weight(1f)
                .clickable {
                    navController.navigate("Home")
                }
        )
        Box1(Modifier.weight(9f)) {
            SearchBar()
        }

    }
}

@Composable
fun BuildSearchScreenContent(appViewModel: AppViewModel,navController: NavController) {
    Column (Modifier.verticalScroll(state = rememberScrollState()),verticalArrangement = Arrangement.spacedBy(15.dp)){
        TrackSectionContent(appViewModel)
        AlbumSectionContent(navController)
        ArtistSectionContent(navController)

        Spacer(Modifier.size(15.dp))
    }
}

@Composable
fun TrackSectionContent(appViewModel: AppViewModel) {
    Column(Modifier.padding(vertical = 10.dp)) {
        Text(
            "Song",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        for (i in 1..3) {
            TracksDetail("ที่รักใจเย็น", listOf("Yanted"),appViewModel)
        }
        Box1(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
    }
}

@Composable
fun AlbumSectionContent(navController: NavController) {
    Column {
        Text(
            "Album",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            )
        ) {
            for (i in 1..3) {
                AlbumBox(navController)
                Spacer(Modifier.size(15.dp))
            }
        }
    }
}

@Composable
fun ArtistSectionContent(navController: NavController) {
    Column {
        Text(
            "Artist",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.W700, color = Color.White),
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier.horizontalScroll(
                state = rememberScrollState(),
            )
        ) {
            for (i in 1..3) {
                ArtistBox(navController)
                Spacer(Modifier.size(15.dp))
            }
        }
    }
}

//image: Image? = null, navController: NavController, albumId : String? = null
@Composable
fun AlbumBox(navController: NavController) {
    Column {
        Box1(
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White).clickable {
//                    navController.navigate("Album/$albumId")
                }
        ) {
            //        AsyncImage(
            //            model = image?.url,
            //            contentDescription = "back",
            //            contentScale = ContentScale.Crop,
            //            modifier = Modifier.fillMaxSize().clickable {
            //                navController.navigate("Album/$albumId")
            //            }
            //        )
        }
        Text(
            "Album name",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W700, color = Color.White), modifier = Modifier.padding(horizontal = 3.dp)
        )
        Text(
            "Artist name",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W700, color = Color.White),modifier = Modifier.padding(horizontal = 3.dp)
        )
    }
}

@Composable
fun ArtistBox(navController: NavController) {
    Column {
        Box1(
            modifier = Modifier
                .size(width = 200.dp, height = 200.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White).clickable {
//                    navController.navigate("")
                }
        ) {
            //        AsyncImage(
            //            model = image?.url,
            //            contentDescription = "back",
            //            contentScale = ContentScale.Crop,
            //            modifier = Modifier.fillMaxSize().clickable {
            //                navController.navigate("Album/$albumId")
            //            }
            //        )
        }
        Text(
            "Artist name",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W700, color = Color.White),modifier = Modifier.padding(horizontal = 3.dp)
        )
    }
}

@Composable
fun TracksDetail(songName: String, artist: List<String>,appViewModel: AppViewModel) {
    Column() {
        Box1(
            Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color(131, 131, 131))
        )
        Box1(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color(35, 35, 35)).clickable {
//                    appViewModel.sendIntent(AppIntent.PlaySong(uri))
                }
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box1(Modifier
                    .size(50.dp)
                    .background(Color.White)) {
                    //Image
                }
                Column(
                    Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
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
                            artist.joinToString(", ") { it },
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
            }
        }

    }
}

@Composable
fun SearchBar() {
    val focusRequester = remember { FocusRequester() }
    var searchText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
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
                "What do you want to listen?",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 1.5.sp
                )
            )
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .height(height = 45.dp)
            .fillMaxWidth().focusRequester(focusRequester)
    )
}