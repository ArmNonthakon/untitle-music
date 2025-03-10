package com.example.core.data.model.album.albumNewReleases

import com.example.core.data.model.album.AlbumResponse
import com.google.gson.annotations.SerializedName

data class AlbumsNewReleaseResponse(
    @SerializedName("albums")val albums: AlbumsResponse
)

data class AlbumsResponse(
    @SerializedName("href") val href: String,
    @SerializedName("limit") val limit: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("offset") val offset: Int,
    @SerializedName("previous") val previous: String?,
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<AlbumResponse>
)


