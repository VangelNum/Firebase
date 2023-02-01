package com.zxcursed.wallpaper.feature_developer.domain.model

import com.zxcursed.wallpaper.feature_notification.domain.model.NotificationToUserData

data class UserPhotos(
    val email: String = "",
    val score: Int = 0,
    val url: MutableList<String> = mutableListOf(),
    val userId: String = "",
)

