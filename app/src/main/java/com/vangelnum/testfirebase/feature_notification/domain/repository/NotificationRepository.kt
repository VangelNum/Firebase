package com.vangelnum.testfirebase.feature_notification.domain.repository

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_notification.domain.model.NotificationToUserData
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotifications(): Flow<Resource<NotificationToUserData>>
}