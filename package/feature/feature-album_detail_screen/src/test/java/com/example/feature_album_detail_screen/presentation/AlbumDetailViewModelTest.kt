package com.example.feature_album_detail_screen.presentation

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.entity.ArtistDetailEntity
import com.example.feature_album_detail_screen.domain.usecase.AlbumDetailGetAlbumUseCase
import com.example.feature_album_detail_screen.domain.usecase.AlbumDetailGetArtistUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AlbumDetailViewModelTest {
    private lateinit var viewModel: AlbumDetailViewModel
    private lateinit var getAlbumUseCase: AlbumDetailGetAlbumUseCase
    private lateinit var getArtistUseCase: AlbumDetailGetArtistUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp(){
        Dispatchers.setMain(StandardTestDispatcher(TestCoroutineScheduler()))
        getAlbumUseCase = mock<AlbumDetailGetAlbumUseCase>()
        getArtistUseCase = mock<AlbumDetailGetArtistUseCase>()
        viewModel = AlbumDetailViewModel(getAlbumUseCase = getAlbumUseCase, getArtistUseCase = getArtistUseCase)
    }

    @Test
    fun `Idle state`() = runTest {
        val state = viewModel.albumDetailState.value.status

        assert(state == AlbumDetailStatus.Idle)
    }

    @Test
    fun `When sent GetAlbumIntent should emit Success state with data AlbumDetailEntity`() = runTest {
        val entity = mock<AlbumDetailEntity>()
        whenever(getAlbumUseCase.execute("albumId")).thenReturn(Result.success(entity))

        viewModel.viewModelScope.launch {
            viewModel.sendIntent(AlbumDetailIntent.GetAlbumResponse("albumId"))
            viewModel.albumDetailState.test {
                assertEquals(AlbumDetailState(), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Loading), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Success,data = entity),awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When sent GetAlbumIntent should emit Failed state with Error message`() = runTest {
        whenever(getAlbumUseCase.execute("albumId")).thenReturn(Result.failure(Exception("Error message")))

        viewModel.viewModelScope.launch {
            viewModel.sendIntent(AlbumDetailIntent.GetAlbumResponse("albumId"))
            viewModel.albumDetailState.test {
                assertEquals(AlbumDetailState(), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Loading), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Failed, message = "Error message"),awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When sent GetArtistIntent should emit success state with data ArtistDetailEntity`() = runTest {
        val entity = mock<ArtistDetailEntity>()
        whenever(getArtistUseCase.execute("artistId")).thenReturn(Result.success(entity))

        viewModel.viewModelScope.launch {
            viewModel.sendIntent(AlbumDetailIntent.GetArtistResponse("artistId"))
            viewModel.albumDetailState.test {
                assertEquals(AlbumDetailState(), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Loading), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Success, artist = entity),awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `When sent GetArtistIntent should emit Failed state with Error message`() = runTest {
        whenever(getArtistUseCase.execute("artistId")).thenReturn(Result.failure(Exception("Error message")))

        viewModel.viewModelScope.launch {
            viewModel.sendIntent(AlbumDetailIntent.GetArtistResponse("artistId"))
            viewModel.albumDetailState.test {
                assertEquals(AlbumDetailState(), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Loading), awaitItem())
                assertEquals(AlbumDetailState(status = AlbumDetailStatus.Failed, message = "Error message"),awaitItem())
                cancelAndConsumeRemainingEvents()
            }
        }
    }


}