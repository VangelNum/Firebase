package com.vangelnum.testfirebase.feature_developer.domain.use_cases

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_developer.domain.model.UserPhotos
import com.vangelnum.testfirebase.feature_developer.domain.repository.DeveloperRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersPhotosUseCase @Inject constructor(
    private val repository: DeveloperRepository,
) {
    operator fun invoke(): Flow<Resource<List<UserPhotos>>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getUsersPhotos()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}