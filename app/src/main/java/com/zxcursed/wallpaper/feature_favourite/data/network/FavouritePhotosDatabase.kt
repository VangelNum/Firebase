package com.zxcursed.wallpaper.feature_favourite.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity


@Database(entities = [FavouritePhotosEntity::class], version = 2)
abstract class FavouritePhotosDatabase : RoomDatabase() {
    abstract fun getDao(): FavouritePhotosDao
}