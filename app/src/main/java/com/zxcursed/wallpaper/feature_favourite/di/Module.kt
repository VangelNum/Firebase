package com.zxcursed.wallpaper.feature_favourite.di

import android.content.Context
import androidx.room.Room
import com.zxcursed.wallpaper.feature_favourite.data.network.FavouritePhotosDao
import com.zxcursed.wallpaper.feature_favourite.data.network.FavouritePhotosDatabase
import com.zxcursed.wallpaper.feature_favourite.data.repository.FavouritePhotosRepositoryImpl
import com.zxcursed.wallpaper.feature_favourite.domain.repository.FavouritePhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideFavouriteDatabase(@ApplicationContext context: Context): FavouritePhotosDatabase {
        synchronized(this) {
            return Room.databaseBuilder(
                context,
                FavouritePhotosDatabase::class.java,
                "database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }


    @Singleton
    @Provides
    fun provideFavouritePhotosDao(appDatabase: FavouritePhotosDatabase): FavouritePhotosDao {
        return appDatabase.getDao()
    }


    @Singleton
    @Provides
    fun provideRepository(myDao: FavouritePhotosDao): FavouritePhotosRepository {
        return FavouritePhotosRepositoryImpl(myDao)
    }
}