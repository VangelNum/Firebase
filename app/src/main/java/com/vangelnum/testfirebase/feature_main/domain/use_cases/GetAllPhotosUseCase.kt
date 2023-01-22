package com.vangelnum.testfirebase.feature_main.domain.use_cases

import com.vangelnum.testfirebase.feature_favourite.common.Resource
import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetAllPhotosUseCase @Inject constructor(
    private val repository: MainRepository,
) {
    operator fun invoke(): Flow<Resource<NewPhotos>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getAllPhotos()
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}