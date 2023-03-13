package com.zxcursed.wallpaper.feature_favourite.di

import com.zxcursed.wallpaper.feature_favourite.data.repository.FavouritePhotosRepositoryImpl
import com.zxcursed.wallpaper.feature_favourite.domain.repository.FavouritePhotosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouriteRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindFavouriteRepository(
        favouriteRepositoryImpl: FavouritePhotosRepositoryImpl
    ): FavouritePhotosRepository
}