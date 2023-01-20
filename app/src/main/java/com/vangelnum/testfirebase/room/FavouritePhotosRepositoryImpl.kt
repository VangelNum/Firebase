package com.vangelnum.testfirebase.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FavouritePhotosRepositoryImpl(
    private val myDao: FavouritePhotosDao
): FavouritePhotosRepository {
    override fun getAllPhotos(): LiveData<List<FavouritePhotosEntity>> {
        return myDao.getAllUsersPhotos()
    }

    override suspend fun addPhoto(photo: FavouritePhotosEntity) {
        return myDao.insertPhoto(photo)
    }

    override suspend fun deletePhoto(photo: FavouritePhotosEntity) {
        return myDao.deletePhoto(photo)
    }

    override suspend fun deletePhotoUrl(url: String) {
        return myDao.deletePhotoUrl(url)
    }
}