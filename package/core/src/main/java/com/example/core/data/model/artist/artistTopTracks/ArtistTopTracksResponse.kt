package com.example.core.data.model.artist.artistTopTracks

import com.example.core.data.model.track.TrackResponse
import com.google.gson.annotations.SerializedName

data class ArtistTopTracksResponse(
    @SerializedName("tracks") val tracks : List<TrackResponse>
)
