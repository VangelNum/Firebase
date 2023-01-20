package com.vangelnum.testfirebase.room

import android.content.Context
import androidx.room.Room
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
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
    fun provideRepository(myDao: FavouritePhotosDao) : FavouritePhotosRepository {
        return FavouritePhotosRepositoryImpl(myDao)
    }
}