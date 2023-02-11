package com.zxcursed.wallpaper.feature_add_photo.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.zxcursed.wallpaper.feature_add_photo.data.AddPhotoRepositoryImpl
import com.zxcursed.wallpaper.feature_add_photo.domain.AddPhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AddPhotoModule {


    @Provides
    @Singleton
    fun provideRepository(
        auth: FirebaseAuth,
        @ApplicationContext context: Context
    ): AddPhotoRepository {
        return AddPhotoRepositoryImpl(auth, context = context)
    }
}