package com.zxcursed.wallpaper.feature_notification.domain.repository

import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.feature_notification.domain.model.NotificationToUserData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotifications(): Flow<Resource<NotificationToUserData>>
}