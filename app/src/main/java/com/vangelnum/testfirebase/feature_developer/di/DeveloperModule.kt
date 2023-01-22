package com.vangelnum.testfirebase.feature_developer.di

import com.vangelnum.testfirebase.feature_developer.data.repository.DeveloperRepositoryImpl
import com.vangelnum.testfirebase.feature_developer.domain.repository.DeveloperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DeveloperModule {

    @Singleton
    @Provides
    fun provideMyRepository(): DeveloperRepository {
        return DeveloperRepositoryImpl()
    }
}