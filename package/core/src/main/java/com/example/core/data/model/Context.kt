package com.example.core.data.model

import com.google.gson.annotations.SerializedName

data class Context(
    @SerializedName("type") val type: String,
    @SerializedName("href") val href: String,
    @SerializedName("external_urls") val externalUrls: ExternalUrls,
    @SerializedName("uri") val uri: String
)
