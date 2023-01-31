package com.zxcursed.wallpaper.feature_favourite.data.repository

import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_favourite.data.network.FavouritePhotosDao
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import com.zxcursed.wallpaper.feature_favourite.domain.repository.FavouritePhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavouritePhotosRepositoryImpl(
    private val myDao: FavouritePhotosDao,
) : FavouritePhotosRepository {
    override fun getAllFavouritesPhotos(): Flow<Resource<List<FavouritePhotosEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val response = myDao.getAllUsersPhotos()
            response.collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }

    override suspend fun addPhoto(photo: FavouritePhotosEntity) {
        return myDao.insertPhoto(photo)
    }

    override suspend fun deletePhotoUrl(url: String) {
        return myDao.deletePhotoUrl(url)
    }


}