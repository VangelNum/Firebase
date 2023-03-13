package com.zxcursed.wallpaper.feature_favourite.domain.repository

import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import kotlinx.coroutines.flow.Flow

interface FavouritePhotosRepository {
    fun getAllFavouritesPhotos(): Flow<Resource<List<FavouritePhotosEntity>>>

    suspend fun addPhoto(photo: FavouritePhotosEntity)

    suspend fun deletePhotoUrl(url: String)
}