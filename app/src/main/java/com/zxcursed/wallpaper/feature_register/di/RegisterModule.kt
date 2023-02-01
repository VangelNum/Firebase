package com.zxcursed.wallpaper.feature_register.di

import com.google.firebase.auth.FirebaseAuth
import com.zxcursed.wallpaper.feature_register.data.repository.RegisterRepositoryImpl
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(auth: FirebaseAuth) : RegisterRepository {
        return RegisterRepositoryImpl(auth)
    }
}