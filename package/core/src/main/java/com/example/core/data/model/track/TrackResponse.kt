package com.example.core.data.model.track


import com.example.core.data.model.ExternalIds
import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Restrictions
import com.example.core.data.model.album.AlbumResponse
import com.example.core.data.model.artist.ArtistResponse

import com.google.gson.annotations.SerializedName



data class TrackResponse(
    @SerializedName("album")
    val album: AlbumResponse,

    @SerializedName("artists")
    val artists: List<ArtistResponse>,

    @SerializedName("available_markets")
    val availableMarkets: List<String>,

    @SerializedName("disc_number")
    val discNumber: Int,

    @SerializedName("duration_ms")
    val durationMs: Int,

    @SerializedName("explicit")
    val explicit: Boolean,

    @SerializedName("external_ids")
    val externalIds: ExternalIds,

    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,

    @SerializedName("href")
    val href: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("is_playable")
    val isPlayable: Boolean,

    @SerializedName("linked_from")
    val linkedFrom: Any? = null,

    @SerializedName("restrictions")
    val restrictions: Restrictions,

    @SerializedName("name")
    val name: String,

    @SerializedName("popularity")
    val popularity: Int,

    @SerializedName("preview_url")
    val previewUrl: String,

    @SerializedName("track_number")
    val trackNumber: Int,

    @SerializedName("type")
    val type: String,

    @SerializedName("uri")
    val uri: String,

    @SerializedName("is_local")
    val isLocal: Boolean
)



