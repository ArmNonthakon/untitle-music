package com.example.feature_home_screen.domain.entity.user

import com.example.core.data.model.ExternalUrls
import com.example.core.data.model.Image


data class UserEntity(
    val displayName: String,
    val externalUrls: ExternalUrls,
    val id: String,
    val images: List<Image>,
)




