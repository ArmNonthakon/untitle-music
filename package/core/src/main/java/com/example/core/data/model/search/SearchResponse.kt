package com.example.core.data.model.search

import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Followers
import com.example.core.data.model.Image
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
    @SerializedName("playlists") val playlists: PlaylistSearchResponse,
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

data class PlaylistSearchResponse(
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
    val items: List<PlaylistResponse?>
)


data class PlaylistResponse(
    @SerializedName("collaborative") val collaborative: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("public") val public: Boolean,
    @SerializedName("snapshot_id") val snapshotId: String,
    @SerializedName("tracks") val tracks: Tracks,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)


data class Owner(
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("display_name") val displayName: String
)



data class Tracks(
    @SerializedName("href") val href: String,
    @SerializedName("total") val total: Int
)