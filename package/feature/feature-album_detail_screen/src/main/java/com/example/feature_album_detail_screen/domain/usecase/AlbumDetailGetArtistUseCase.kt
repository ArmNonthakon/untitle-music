package com.example.feature_album_detail_screen.domain.usecase

import com.example.core.data.model.artist.ArtistResponse
import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepository
import com.example.feature_album_detail_screen.domain.entity.ArtistDetailEntity
import com.example.feature_album_detail_screen.domain.mapper.ArtistDetailMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AlbumDetailGetArtistUseCase @Inject constructor(
    private val repository: AlbumDetailRepository,
    private val mapper : ArtistDetailMapper,
) {
    suspend fun execute(artistId: String, callback: ArtistDetailCallBack) {
        val call = repository.getArtist(artistId)
        call.enqueue(object : Callback<ArtistResponse> {
            override fun onResponse(
                call: Call<ArtistResponse>,
                response: Response<ArtistResponse>
            ) {
                if (response.isSuccessful) {
                    val artist = response.body()
                    val result = artist?.let { mapper.mapper(it) }
                    callback.onSuccess(result)
                } else {
                    callback.onError(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ArtistResponse>, t: Throwable) {
                callback.onError("Error : $t")
            }
        })


    }

}

interface ArtistDetailCallBack {
    fun onSuccess(entity: ArtistDetailEntity?)
    fun onError(errorMessage: String)
}