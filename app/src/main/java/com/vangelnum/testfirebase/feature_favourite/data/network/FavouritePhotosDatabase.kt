package com.vangelnum.testfirebase.feature_favourite.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity


@Database(entities = [FavouritePhotosEntity::class], version = 2)
abstract class FavouritePhotosDatabase : RoomDatabase() {
    abstract fun getDao(): FavouritePhotosDao
}