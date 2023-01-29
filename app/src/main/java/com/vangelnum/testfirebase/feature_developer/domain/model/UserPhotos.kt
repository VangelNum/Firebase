package com.vangelnum.testfirebase.feature_developer.domain.model

import com.vangelnum.testfirebase.feature_notification.domain.model.NotificationToUserData

data class UserPhotos(
    val email: String = "",
    val score: Int = 0,
    val url: MutableList<String> = mutableListOf(),
    val userId: String = "",
    val notification: MutableList<NotificationToUserData> = mutableListOf(),
)