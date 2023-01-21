package com.vangelnum.testfirebase.feature_favourite.domain.use_case

import com.vangelnum.testfirebase.feature_favourite.common.Resource
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import com.vangelnum.testfirebase.feature_favourite.domain.repository.FavouritePhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavouriteUseCase @Inject constructor(
    private val repository: FavouritePhotosRepository,
) {

    operator fun invoke() : Flow<List<FavouritePhotosEntity>> {
        return repository.getAllPhotos()
    }

//    operator fun invoke(): Flow<Resource<List<FavouritePhotosEntity>>> = flow {
//        try {
//            emit(Resource.Loading())
//            val favourite = repository.getAllPhotos()
//            emit(Resource.Success(favourite))
//
//        } catch (e: Exception) {
//
//        }
//    }
}