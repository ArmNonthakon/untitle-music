package com.example.feature_home_screen.domain.mapper

import com.example.core.data.model.user.UserResponse
import com.example.feature_home_screen.domain.entity.user.UserEntity
import javax.inject.Inject

class HomeScreenUserMapper @Inject constructor(){
    fun map(userResponse: UserResponse): UserEntity {
        return UserEntity(
            displayName = userResponse.displayName,
            externalUrls = userResponse.externalUrls,
            id = userResponse.id,
            images = userResponse.images,
        )
    }
}