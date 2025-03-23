package com.example.core.data.model.search

import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.getAlbumResponse.ArtistAlbumResponse
import com.example.core.data.model.getAlbumResponse.TracksAlbumResponse
import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("tracks") val tracks: TrackSearchResponse,
    @SerializedName("artists") val artists: ArtistSearchResponse,
    @SerializedName("albums") val albums: AlbumSearchResponse,
)

data class TrackSearchResponse(
    @SerializedName("href")
    val href: String,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("total")
    val total: Int,

    @SerializedName("items")
    val items: List<TrackResponse>
)

data class ArtistSearchResponse(
    @SerializedName("href")
    val href: String,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("total")
    val total: Int,

    @SerializedName("items")
    val items: List<ArtistResponse>
)

data class AlbumSearchResponse(
    @SerializedName("href")
    val href: String,

    @SerializedName("limit")
    val limit: Int,

    @SerializedName("next")
    val next: String?,

    @SerializedName("offset")
    val offset: Int,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("total")
    val total: Int,

    @SerializedName("items")
    val items: List<AlbumResponse>
)
