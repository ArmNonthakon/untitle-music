package com.example.core.data.model.user

import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Followers
import com.example.core.data.model.Image
import com.google.gson.annotations.SerializedName


data class UserResponse(
    @SerializedName("display_name")
    val displayName: String,

    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,

    @SerializedName("followers")
    val followers: Followers,

    @SerializedName("href")
    val href: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("images")
    val images: List<Image>,

    @SerializedName("type")
    val type: String,

    @SerializedName("uri")
    val uri: String
)




