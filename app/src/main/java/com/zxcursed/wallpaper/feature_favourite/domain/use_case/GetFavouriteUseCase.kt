package com.zxcursed.wallpaper.feature_favourite.domain.use_case

import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import com.zxcursed.wallpaper.feature_favourite.domain.repository.FavouritePhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val repository: FavouritePhotosRepository,
) {

    operator fun invoke(): Flow<Resource<List<FavouritePhotosEntity>>> {
        return repository.getAllFavouritesPhotos()
    }
}