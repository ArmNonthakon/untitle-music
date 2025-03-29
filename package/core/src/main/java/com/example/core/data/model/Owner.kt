package com.example.core.data.model

import com.google.gson.annotations.SerializedName


data class Owner(
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("display_name") val displayName: String
)
