package com.vangelnum.testfirebase.feature_favourite.domain.use_case

import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import com.vangelnum.testfirebase.feature_favourite.domain.repository.FavouritePhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val repository: FavouritePhotosRepository,
) {

    operator fun invoke(): Flow<Resource<List<FavouritePhotosEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getAllFavouritesPhotos()
            response.collect {
                emit(Resource.Success(it))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}