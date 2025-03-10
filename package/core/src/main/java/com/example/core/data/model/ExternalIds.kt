package com.example.core.data.model

import com.google.gson.annotations.SerializedName

data class ExternalIds(
    @SerializedName("isrc")
    val isrc: String,

    @SerializedName("ean")
    val ean: String,

    @SerializedName("upc")
    val upc: String
)
