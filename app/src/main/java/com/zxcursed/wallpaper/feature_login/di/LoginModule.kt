package com.zxcursed.wallpaper.feature_login.di

import com.google.firebase.auth.FirebaseAuth
import com.zxcursed.wallpaper.feature_login.data.repository.LoginRepositoryImpl
import com.zxcursed.wallpaper.feature_login.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginRepository(auth: FirebaseAuth) : LoginRepository {
        return LoginRepositoryImpl(auth)
    }
}