package com.vangelnum.testfirebase.feature_notification.presentation

import com.vangelnum.testfirebase.feature_notification.domain.model.NotificationToUserData

data class NotificationStates(
    var isLoading: Boolean = false,
    var data: NotificationToUserData = NotificationToUserData(),
    var error: String = ""
)