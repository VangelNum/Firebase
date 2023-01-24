package com.vangelnum.testfirebase.feature_main.domain.use_cases

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllPhotosUseCase @Inject constructor(
    private val repository: MainRepository,
) {
    suspend operator fun invoke(): Flow<Resource<NewPhotos>> {
        return repository.getAllPhotos()
    }
}