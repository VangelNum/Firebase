package com.vangelnum.testfirebase.feature_favourite.domain.repository

import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import kotlinx.coroutines.flow.Flow

interface FavouritePhotosRepository {
    fun getAllPhotos(): Flow<List<FavouritePhotosEntity>>

    suspend fun addPhoto(photo: FavouritePhotosEntity)

    suspend fun deletePhotoUrl(url: String)
}