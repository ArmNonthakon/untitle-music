package com.example.core.data.model.track.trackYourTopResponse

import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

data class YourTopTrackResponse(
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

