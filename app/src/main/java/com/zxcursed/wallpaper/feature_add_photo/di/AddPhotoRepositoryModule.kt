package com.zxcursed.wallpaper.feature_add_photo.di

import com.zxcursed.wallpaper.feature_add_photo.data.AddPhotoRepositoryImpl
import com.zxcursed.wallpaper.feature_add_photo.domain.AddPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AddPhotoRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAddPhotoRepository(
        addPhotoRepositoryImpl: AddPhotoRepositoryImpl
    ): AddPhotoRepository
}