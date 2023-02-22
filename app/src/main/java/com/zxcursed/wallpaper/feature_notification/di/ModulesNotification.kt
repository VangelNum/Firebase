package com.zxcursed.wallpaper.feature_notification.di

import com.google.firebase.auth.FirebaseAuth
import com.zxcursed.wallpaper.feature_notification.data.repository.NotificationRepositoryImpl
import com.zxcursed.wallpaper.feature_notification.domain.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModulesNotification {
    @Provides
    @Singleton
    fun provideNotificationRepository(auth: FirebaseAuth): NotificationRepository {
        return NotificationRepositoryImpl(auth)
    }
}