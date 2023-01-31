package com.zxcursed.wallpaper.feature_favourite.domain.use_case

import com.zxcursed.wallpaper.feature_favourite.domain.repository.FavouritePhotosRepository
import javax.inject.Inject

class DeleteFavouriteUseCase @Inject constructor(
    private val repository: FavouritePhotosRepository,
) {
    suspend operator fun invoke(url: String) {
        return repository.deletePhotoUrl(url)
    }
}