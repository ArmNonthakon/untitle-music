package com.example.core.data.model.artist

import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Followers
import com.example.core.data.model.Image
import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<Image>,
    @SerializedName("name") val name: String,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)
