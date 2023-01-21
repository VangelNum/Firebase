package com.vangelnum.testfirebase.feature_main.domain.use_cases

import android.util.Log
import com.vangelnum.testfirebase.feature_favourite.common.Resource
import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetAllPhotosUseCase @Inject constructor(
    private val repository: MainRepository,
) {
   operator fun invoke(): Flow<Resource<NewPhotos>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getAllPhotos()
            response.collect {
                emit(Resource.Success(it))
            }

            Log.d("response", response.toString())
        } catch (e: Exception) {
            Log.d("response", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }
}