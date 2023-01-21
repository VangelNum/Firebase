package com.vangelnum.testfirebase.room

import androidx.lifecycle.LiveData

interface FavouritePhotosRepository {
    fun getAllPhotos(): LiveData<List<FavouritePhotosEntity>>

    suspend fun addPhoto(photo: FavouritePhotosEntity)

    suspend fun deletePhoto(photo: FavouritePhotosEntity)

    suspend fun deletePhotoUrl(photo: String)
}