package com.zxcursed.wallpaper.feature_notification.di

import com.zxcursed.wallpaper.feature_notification.data.repository.NotificationRepositoryImpl
import com.zxcursed.wallpaper.feature_notification.domain.repository.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationRepositoryModule {
    @Binds
    @Singleton
    abstract fun provideNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}