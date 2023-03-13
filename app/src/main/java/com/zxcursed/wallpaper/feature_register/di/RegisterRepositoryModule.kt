package com.zxcursed.wallpaper.feature_register.di

import com.zxcursed.wallpaper.feature_register.data.repository.RegisterRepositoryImpl
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

}