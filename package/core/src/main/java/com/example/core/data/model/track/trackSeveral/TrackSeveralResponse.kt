package com.example.core.data.model.track.trackSeveral


import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

data class TrackSeveralResponse(
    @SerializedName("tracks") val tracks : List<TrackResponse>
)
