package com.zxcursed.wallpaper.feature_developer.data.dto

import com.zxcursed.wallpaper.feature_notification.domain.model.NotificationToUserData

data class UserPhotosDto(
    val email: String = "",
    val score: Int = 0,
    val url: MutableList<String> = mutableListOf(),
    val userId: String = "",
    val notification: MutableList<NotificationToUserData> = mutableListOf(),
)
