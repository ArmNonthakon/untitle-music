package com.example.core.data.model.album


import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Image
import com.example.core.data.model.Restrictions
import com.example.core.data.model.artist.ArtistResponse
import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

data class AlbumResponse(
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
    @SerializedName("restrictions") val restrictions: Restrictions,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("artists") val artists: List<ArtistResponse>,
    @SerializedName("tracks") val tracks: List<TrackResponse>,
)
