package com.example.core.data.model.getAlbumResponse

import com.example.core.data.model.ExternalIds
import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Image
import com.example.core.data.model.Restrictions
import com.google.gson.annotations.SerializedName

data class GetAlbumResponse(
    @SerializedName("album_type") val albumType: String,
    @SerializedName("total_tracks") val totalTracks: Int,
    @SerializedName("available_markets") val availableMarkets: List<String>,
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("name") val name: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("release_date_precision") val releaseDatePrecision: String,
    @SerializedName("restrictions") val restrictions: Restrictions?,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("artists") val artists: List<ArtistAlbumResponse>,
    @SerializedName("tracks") val tracks: TracksAlbumResponse,
    @SerializedName("copyrights") val copyrights: List<Copyright>,
    @SerializedName("external_ids") val externalIds: ExternalIds,
    @SerializedName("genres") val genres: List<String>,  // Can be empty
    @SerializedName("label") val label: String,
    @SerializedName("popularity") val popularity: Int
)

data class ArtistAlbumResponse(
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)

data class TracksAlbumResponse(
    @SerializedName("href") val href: String,
    @SerializedName("limit") val limit: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("offset") val offset: Int,
    @SerializedName("previous") val previous: String?,
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<TrackItemAlbumResponse>
)

data class TrackItemAlbumResponse(
    @SerializedName("artists") val artists: List<ArtistAlbumResponse>,
    @SerializedName("available_markets") val availableMarkets: List<String>,
    @SerializedName("disc_number") val discNumber: Int,
    @SerializedName("duration_ms") val durationMs: Int,
    @SerializedName("explicit") val explicit: Boolean,
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("is_playable") val isPlayable: Boolean,
    @SerializedName("linked_from") val linkedFrom: LinkedFrom?,
    @SerializedName("restrictions") val restrictions: Restrictions?,
    @SerializedName("name") val name: String,
    @SerializedName("preview_url") val previewUrl: String?,
    @SerializedName("track_number") val trackNumber: Int,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("is_local") val isLocal: Boolean
)

data class LinkedFrom(
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)

data class Copyright(
    @SerializedName("text") val text: String,
    @SerializedName("type") val type: String
)

