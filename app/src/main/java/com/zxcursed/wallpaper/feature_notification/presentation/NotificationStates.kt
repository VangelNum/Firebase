package com.zxcursed.wallpaper.feature_notification.presentation

import com.zxcursed.wallpaper.feature_notification.domain.model.NotificationToUserData

data class NotificationStates(
    var isLoading: Boolean = false,
    var data: NotificationToUserData = NotificationToUserData(),
    var error: String = ""
)