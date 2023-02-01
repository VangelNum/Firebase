package com.zxcursed.wallpaper.feature_developer.data.mapper

import com.zxcursed.wallpaper.feature_developer.data.dto.UserPhotosDto
import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos

fun UserPhotosDto.toUserPhotos(): UserPhotos {
    return UserPhotos(
        email = email,
        score = score,
        url = url,
        userId = userId,
    )
}