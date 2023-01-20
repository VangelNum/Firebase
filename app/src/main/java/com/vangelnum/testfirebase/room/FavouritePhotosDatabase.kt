package com.vangelnum.testfirebase.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavouritePhotosEntity::class], version = 2)
abstract class FavouritePhotosDatabase : RoomDatabase() {
    abstract fun getDao(): FavouritePhotosDao
}