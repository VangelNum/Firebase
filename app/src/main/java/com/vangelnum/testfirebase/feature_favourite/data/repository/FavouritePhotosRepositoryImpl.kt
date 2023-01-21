package com.vangelnum.testfirebase.feature_favourite.data.repository

import com.vangelnum.testfirebase.feature_favourite.data.network.FavouritePhotosDao
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import com.vangelnum.testfirebase.feature_favourite.domain.repository.FavouritePhotosRepository
import kotlinx.coroutines.flow.Flow

class FavouritePhotosRepositoryImpl(
    private val myDao: FavouritePhotosDao,
) : FavouritePhotosRepository {
    override fun getAllPhotos(): Flow<List<FavouritePhotosEntity>> {
        return myDao.getAllUsersPhotos()
    }

    override suspend fun addPhoto(photo: FavouritePhotosEntity) {
        return myDao.insertPhoto(photo)
    }

    override suspend fun deletePhotoUrl(url: String) {
        return myDao.deletePhotoUrl(url)
    }



}