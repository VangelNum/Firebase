package com.vangelnum.testfirebase.feature_notification.di

import com.vangelnum.testfirebase.feature_notification.data.repository.NotificationRepositoryImpl
import com.vangelnum.testfirebase.feature_notification.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModulesNotification
{
    @Provides
    @Singleton
    fun provideNotificationRepository() : NotificationRepository {
        return NotificationRepositoryImpl()
    }
}