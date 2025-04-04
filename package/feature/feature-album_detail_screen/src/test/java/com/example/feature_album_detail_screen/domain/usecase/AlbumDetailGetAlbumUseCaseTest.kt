package com.example.feature_album_detail_screen.domain.usecase

import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepository
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.mapper.AlbumDetailMapper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class AlbumDetailGetAlbumUseCaseTest {
    private lateinit var useCase: AlbumDetailGetAlbumUseCase
    private lateinit var mapper: AlbumDetailMapper
    private lateinit var repository: AlbumDetailRepository

    @Before
    fun setUp() {
        mapper = mock<AlbumDetailMapper>()
        repository = mock<AlbumDetailRepository>()
        useCase = AlbumDetailGetAlbumUseCase(repository, mapper)
    }

    @Test
    fun `when excute should return success wiht AlbumDetailEntity`(): Unit = runBlocking {
        val mockResponse = mock<GetAlbumResponse>()
        val mockEntity = mock<AlbumDetailEntity>()
        val expected = Result.success(mockEntity)
        whenever(repository.getAlbum("albumId")).thenReturn(Response.success(mockResponse))
        whenever(mapper.mapper(mockResponse)).thenReturn(mockEntity)
        val actual = useCase.execute("albumId")
        assertEquals(expected, actual)
    }

    @Test
    fun `when execute should return failure with no data response`(): Unit = runBlocking {
        val expected = Result.failure<Exception>(Exception("No data response"))
        whenever(repository.getAlbum("albumId")).thenReturn(Response.success(null))
        val actual = useCase.execute("albumId")
        assertEquals(expected.isFailure, actual.isFailure)
        assertEquals(expected.exceptionOrNull()?.message, actual.exceptionOrNull()?.message)
    }

    @Test
    fun `when execute should return failure with error message with code`() = runBlocking {
        val errorResponseBody = "{\"message\":\"Error\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())

        whenever(repository.getAlbum("albumId"))
            .thenReturn(Response.error(400, errorResponseBody))

        val actual = useCase.execute("albumId")

        assert(actual.isFailure)
    }

    @Test
    fun `when execute should return failure with error message`() = runBlocking {
        val expectedMessage = "Error"

        whenever(repository.getAlbum("albumId")).thenThrow(RuntimeException("Error"))

        val actual = useCase.execute("albumId")

        assert(actual.isFailure)
        assertEquals(expectedMessage, actual.exceptionOrNull()?.message)
    }
}