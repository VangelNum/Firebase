package com.zxcursed.wallpaper.feature_developer_join.di

import com.zxcursed.wallpaper.feature_developer_join.data.DeveloperJoinRepositoryImpl
import com.zxcursed.wallpaper.feature_developer_join.domain.DeveloperJoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DeveloperJoinRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDeveloperJoinRepository(
        developerJoinRepositoryImpl: DeveloperJoinRepositoryImpl
    ): DeveloperJoinRepository
}