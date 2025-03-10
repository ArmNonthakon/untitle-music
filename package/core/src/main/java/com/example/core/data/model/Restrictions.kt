package com.example.core.data.model

import com.google.gson.annotations.SerializedName

data class Restrictions(
    @SerializedName("reason") val reason: String
)

