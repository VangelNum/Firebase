package com.zxcursed.wallpaper.feature_main.domain.use_cases

import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.feature_main.domain.model.NewPhotos
import com.zxcursed.wallpaper.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllPhotosUseCase @Inject constructor(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(): Flow<Resource<NewPhotos>> {
        return repository.getAllPhotos()
    }
}