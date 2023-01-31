package com.zxcursed.wallpaper.feature_developer.domain.use_cases

import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos
import com.zxcursed.wallpaper.feature_developer.domain.repository.DeveloperRepository
import javax.inject.Inject

class DeleteUsersPhotosUseCase @Inject constructor(
    private val repository: DeveloperRepository
) {
    suspend operator fun invoke(onePhoto: String, collection: UserPhotos) {
        return repository.deleteUsersPhotosFromDeveleoper(onePhoto, collection)
    }
}