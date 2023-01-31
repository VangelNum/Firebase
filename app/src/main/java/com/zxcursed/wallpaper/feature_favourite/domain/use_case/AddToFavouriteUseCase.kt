package com.zxcursed.wallpaper.feature_favourite.domain.use_case

import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import com.zxcursed.wallpaper.feature_favourite.domain.repository.FavouritePhotosRepository
import javax.inject.Inject

class AddToFavouriteUseCase @Inject constructor(
    private val repository: FavouritePhotosRepository,
) {
    suspend operator fun invoke(photo: FavouritePhotosEntity) {
        return repository.addPhoto(photo)
    }
}