package com.example.feature_album_detail_screen.domain.usecase

import com.example.core.data.model.getAlbumResponse.GetAlbumResponse
import com.example.feature_album_detail_screen.data.repository.AlbumDetailRepository
import com.example.feature_album_detail_screen.domain.entity.AlbumDetailEntity
import com.example.feature_album_detail_screen.domain.mapper.AlbumDetailMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AlbumDetailGetAlbumUseCase @Inject constructor(
    private val repository: AlbumDetailRepository,
    private val mapper: AlbumDetailMapper
) {
    suspend fun execute(albumId: String, callback: AlbumDetailCallBack) {
        val call = repository.getAlbum(albumId)
        call.enqueue(object : Callback<GetAlbumResponse> {
            override fun onResponse(
                call: Call<GetAlbumResponse>,
                response: Response<GetAlbumResponse>
            ) {
                if (response.isSuccessful) {
                    val album = response.body()
                    val result = album?.let { mapper.mapper(it) }
                    callback.onSuccess(result)
                } else {
                    callback.onError(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<GetAlbumResponse>, t: Throwable) {
                callback.onError("Error : $t")
            }
        })


    }

}

interface AlbumDetailCallBack {
    fun onSuccess(entity: AlbumDetailEntity?)
    fun onError(errorMessage: String)
}