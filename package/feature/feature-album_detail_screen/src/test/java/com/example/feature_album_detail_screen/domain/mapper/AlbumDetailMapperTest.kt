package com.example.feature_album_detail_screen.domain.mapper

import com.example.core.data.model.ExternalIds
import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Image
import com.example.core.data.model.getAlbumResponse.ArtistAlbumResponse
import com.example.core.data.model.getAlbumResponse.Copyright
import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.core.data.model.getAlbumResponse.TrackItemAlbumResponse
import com.example.core.data.model.getAlbumResponse.TracksAlbumResponse
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AlbumDetailMapperTest {
    private lateinit var mapper : AlbumDetailMapper

    @Before
    fun setUp(){
        mapper = AlbumDetailMapper()
    }

    @Test
    fun `when call mapper should return AlbumDetailEntity `(): Unit = runBlocking {
        val mockAlbumResponse = GetAlbumResponse(
            albumType = "album",
            totalTracks = 12,
            availableMarkets = listOf("US", "GB", "FR"),
            externalUrls = ExternalUrls("https://open.spotify.com/album/12345"),
            href = "https://api.spotify.com/v1/albums/12345",
            id = "12345",
            images = listOf(
                Image(640,"https://i.scdn.co/image/abc", 640),
                Image(300,"https://i.scdn.co/image/def",  300),
                Image(64,"https://i.scdn.co/image/ghi",  64)
            ),
            name = "Sample Album",
            releaseDate = "2024-03-15",
            releaseDatePrecision = "day",
            restrictions = null,
            type = "album",
            uri = "spotify:album:12345",
            artists = listOf(
                ArtistAlbumResponse(
                    externalUrls = ExternalUrls("https://open.spotify.com/artist/6789"),
                    href = "https://api.spotify.com/v1/artists/6789",
                    id = "6789",
                    name = "Sample Artist",
                    type = "artist",
                    uri = "spotify:artist:6789"
                )
            ),
            tracks = TracksAlbumResponse(
                href = "https://api.spotify.com/v1/albums/12345/tracks",
                limit = 10,
                next = null,
                offset = 0,
                previous = null,
                total = 10,
                items = listOf(
                    TrackItemAlbumResponse(
                        artists = listOf(
                            ArtistAlbumResponse(
                                externalUrls = ExternalUrls("https://open.spotify.com/artist/6789"),
                                href = "https://api.spotify.com/v1/artists/6789",
                                id = "6789",
                                name = "Sample Artist",
                                type = "artist",
                                uri = "spotify:artist:6789"
                            )
                        ),
                        availableMarkets = listOf("US", "GB"),
                        discNumber = 1,
                        durationMs = 210000,
                        explicit = false,
                        externalUrls = ExternalUrls("https://open.spotify.com/track/abc123"),
                        href = "https://api.spotify.com/v1/tracks/abc123",
                        id = "abc123",
                        isPlayable = true,
                        linkedFrom = null,
                        restrictions = null,
                        name = "Sample Track",
                        previewUrl = "https://p.scdn.co/mp3-preview/xyz",
                        trackNumber = 1,
                        type = "track",
                        uri = "spotify:track:abc123",
                        isLocal = false
                    )
                )
            ),
            copyrights = listOf(
                Copyright(text = "© 2024 Sample Label", type = "C")
            ),
            externalIds = ExternalIds(isrc = "isrc", ean = "ean", upc = "upc"),
            genres = listOf("Pop", "Rock"),
            label = "Sample Label",
            popularity = 85
        )
        val expectResult = AlbumDetailEntity(
            totalTracks = 12,
            id = "12345",
            images = listOf(
                Image(640,"https://i.scdn.co/image/abc", 640),
                Image(300,"https://i.scdn.co/image/def",  300),
                Image(64,"https://i.scdn.co/image/ghi",  64)
            ),
            name = "Sample Album",
            uri = "spotify:album:12345",
            artists = listOf(
                ArtistAlbumResponse(
                    externalUrls = ExternalUrls("https://open.spotify.com/artist/6789"),
                    href = "https://api.spotify.com/v1/artists/6789",
                    id = "6789",
                    name = "Sample Artist",
                    type = "artist",
                    uri = "spotify:artist:6789"
                )
            ),
            tracks = TracksAlbumResponse(
                href = "https://api.spotify.com/v1/albums/12345/tracks",
                limit = 10,
                next = null,
                offset = 0,
                previous = null,
                total = 10,
                items = listOf(
                    TrackItemAlbumResponse(
                        artists = listOf(
                            ArtistAlbumResponse(
                                externalUrls = ExternalUrls("https://open.spotify.com/artist/6789"),
                                href = "https://api.spotify.com/v1/artists/6789",
                                id = "6789",
                                name = "Sample Artist",
                                type = "artist",
                                uri = "spotify:artist:6789"
                            )
                        ),
                        availableMarkets = listOf("US", "GB"),
                        discNumber = 1,
                        durationMs = 210000,
                        explicit = false,
                        externalUrls = ExternalUrls("https://open.spotify.com/track/abc123"),
                        href = "https://api.spotify.com/v1/tracks/abc123",
                        id = "abc123",
                        isPlayable = true,
                        linkedFrom = null,
                        restrictions = null,
                        name = "Sample Track",
                        previewUrl = "https://p.scdn.co/mp3-preview/xyz",
                        trackNumber = 1,
                        type = "track",
                        uri = "spotify:track:abc123",
                        isLocal = false
                    )
                )
            ),
        )

        val actualResult = mapper.mapper(mockAlbumResponse)
        assertEquals(expectResult,actualResult)

    }
}