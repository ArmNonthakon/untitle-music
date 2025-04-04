package com.example.feature_album_detail_screen.data.repository

import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.core.data.service.ApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class AlbumDetailRepositoryImplTest {
    private lateinit var service : ApiService
    private lateinit var repository: AlbumDetailRepository

    @Before
    fun setUp(){
        service = mock<ApiService>()
        repository = AlbumDetailRepositoryImpl(service)
    }

    @Test
    fun `getAlbum should return GetAlbumResponse`(): Unit = runBlocking {
        val albumId = "albumId"
        val mockResponse : GetAlbumResponse = mock<GetAlbumResponse>()
        whenever(service.getAlbum(albumId)).thenReturn(Response.success(mockResponse))
        val result = repository.getAlbum(albumId)

        assertEquals(mockResponse, result.body())
        verify(service).getAlbum(albumId)
    }

    @Test
    fun `getArtist should return ArtistResponse`(): Unit = runBlocking {
        val artistId = "artistId"
        val mockResponse : ArtistResponse = mock<ArtistResponse>()
        whenever(service.getArtist(artistId)).thenReturn(Response.success(mockResponse))
        val result = repository.getArtist(artistId)

        assertEquals(mockResponse, result.body())
        verify(service).getArtist(artistId)
    }
}