package com.vangelnum.testfirebase.feature_favourite.domain.repository

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import kotlinx.coroutines.flow.Flow

interface FavouritePhotosRepository {
    fun getAllFavouritesPhotos(): Flow<Resource<List<FavouritePhotosEntity>>>

    suspend fun addPhoto(photo: FavouritePhotosEntity)

    suspend fun deletePhotoUrl(url: String)
}