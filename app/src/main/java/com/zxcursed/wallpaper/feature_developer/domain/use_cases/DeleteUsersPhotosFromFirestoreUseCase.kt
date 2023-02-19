package com.zxcursed.wallpaper.feature_developer.domain.use_cases

import com.zxcursed.wallpaper.feature_developer.domain.repository.DeveloperRepository
import javax.inject.Inject

class DeleteUsersPhotosFromFirestoreUseCase @Inject constructor(
    private val repository: DeveloperRepository
) {
    suspend operator fun invoke(onePhoto: String) {
        return repository.deleteUsersPhotosFromDeveloperFirestore(onePhoto)
    }
}