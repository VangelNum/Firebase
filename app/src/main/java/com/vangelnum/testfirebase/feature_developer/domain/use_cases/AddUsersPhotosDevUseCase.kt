package com.vangelnum.testfirebase.feature_developer.domain.use_cases

import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import com.vangelnum.testfirebase.feature_developer.domain.repository.DeveloperRepository
import javax.inject.Inject

class AddUsersPhotosDevUseCase @Inject constructor(
    private val repository: DeveloperRepository
) {
    suspend operator fun invoke(onePhoto: String,collectPhotos: UserPhotos) {
        return repository.addUsersPhotosFromDeveloper(onePhoto, collectPhotos)
    }
}