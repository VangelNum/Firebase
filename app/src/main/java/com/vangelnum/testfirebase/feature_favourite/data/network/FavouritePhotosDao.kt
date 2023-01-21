package com.vangelnum.testfirebase.feature_favourite.data.network

import androidx.room.*
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouritePhotosDao {
    @Query("SELECT * FROM photos_table")
    fun getAllUsersPhotos(): Flow<List<FavouritePhotosEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photo: FavouritePhotosEntity)

    @Query("DELETE FROM photos_table WHERE url = :url")
    suspend fun deletePhotoUrl(url: String)
}