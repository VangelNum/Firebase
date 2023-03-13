package com.zxcursed.wallpaper.feature_developer.di

import com.zxcursed.wallpaper.feature_developer.data.repository.DeveloperRepositoryImpl
import com.zxcursed.wallpaper.feature_developer.domain.repository.DeveloperRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DeveloperRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDeveloperRepository(
        developerRepositoryImpl: DeveloperRepositoryImpl
    ): DeveloperRepository
}