package com.vangelnum.testfirebase.feature_main.di

import com.vangelnum.testfirebase.feature_main.data.repository.MainRepositoryImpl
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Singleton
    @Provides
    fun provideMyRepository(): MainRepository {
        return MainRepositoryImpl()
    }
}