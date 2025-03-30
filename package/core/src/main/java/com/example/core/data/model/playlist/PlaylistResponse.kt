package com.example.core.data.model.playlist

import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Image
import com.example.core.data.model.Owner
import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

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
    @SerializedName("tracks") val tracks: TracksPlaylistResponse,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)

data class TracksPlaylistResponse(
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
    val items: List<TrackPlaylistItemResponse>
)

data class TrackPlaylistItemResponse(
    @SerializedName("added_at")
    val addedAt: String?,

    @SerializedName("added_by")
    val addedBy: AddedBy?,

    @SerializedName("is_local")
    val isLocal: Boolean,

    @SerializedName("track")
    val track: TrackResponse,
)

data class AddedBy(
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)