package com.example.feature_home_screen.domain.entity.artist.artistYourTop

import com.example.feature_home_screen.domain.entity.artist.ArtistEntity

data class ArtistYourTopEntity(
    val total: Int,
    val items: List<ArtistEntity>
)