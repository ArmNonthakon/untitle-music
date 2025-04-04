package com.example.feature_album_detail_screen.domain.mapper


import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Followers
import com.example.core.data.model.Image
import com.example.core.data.model.artist.ArtistResponse
import com.example.feature_album_detail_screen.domain.entity.ArtistDetailEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ArtistDetailMapperTest {
    private lateinit var mapper : ArtistDetailMapper

    @Before
    fun setUp(){
        mapper = ArtistDetailMapper()
    }

    @Test
    fun `when call mapper should return ArtistDetailEntity`(): Unit = runBlocking {
        val mockArtistResponse = ArtistResponse(
            externalUrls = ExternalUrls("https://open.spotify.com/artist/6789"),
            followers = Followers(total = 5000000, href = "href"),
            genres = listOf("Pop", "Rock", "Indie"),
            href = "https://api.spotify.com/v1/artists/6789",
            id = "6789",
            images = listOf(
                Image(640,"https://i.scdn.co/image/xyz1",  640),
                Image(300,"https://i.scdn.co/image/xyz2",  300),
                Image(64,"https://i.scdn.co/image/xyz3",  64)
            ),
            name = "Sample Artist",
            popularity = 90,
            type = "artist",
            uri = "spotify:artist:6789"
        )

        val expectedResult = ArtistDetailEntity(id = "6789", images = listOf(
            Image(640,"https://i.scdn.co/image/xyz1",  640),
            Image(300,"https://i.scdn.co/image/xyz2",  300),
            Image(64,"https://i.scdn.co/image/xyz3",  64)
        ),)

        val actualResult = mapper.mapper(mockArtistResponse)
        assertEquals(expectedResult,actualResult)

    }
}