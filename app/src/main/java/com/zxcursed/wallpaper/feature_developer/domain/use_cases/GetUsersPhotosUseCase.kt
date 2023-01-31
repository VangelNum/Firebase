package com.zxcursed.wallpaper.feature_developer.domain.use_cases

import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos
import com.zxcursed.wallpaper.feature_developer.domain.repository.DeveloperRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersPhotosUseCase @Inject constructor(
    private val repository: DeveloperRepository,
) {
    suspend operator fun invoke(): Flow<Resource<List<UserPhotos>>> {
        return repository.getUsersPhotos()
    }
}