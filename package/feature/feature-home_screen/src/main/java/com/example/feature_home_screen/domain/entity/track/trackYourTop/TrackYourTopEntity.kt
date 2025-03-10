package com.example.feature_home_screen.domain.entity.track.trackYourTop

import com.example.feature_home_screen.domain.entity.track.TrackEntity

data class TrackYourTopEntity(
    val total: Int,
    val items: List<TrackEntity>
)