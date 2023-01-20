package com.vangelnum.testfirebase.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.StateFlow


@Dao
interface FavouritePhotosDao {
    @Query ("SELECT * FROM photos_table")
    fun getAllUsersPhotos(): LiveData<List<FavouritePhotosEntity>>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photo: FavouritePhotosEntity)

    @Delete
    suspend fun deletePhoto (photo: FavouritePhotosEntity)

    @Query ("DELETE FROM photos_table WHERE url = :url")
    suspend fun deletePhotoUrl(url: String)
}