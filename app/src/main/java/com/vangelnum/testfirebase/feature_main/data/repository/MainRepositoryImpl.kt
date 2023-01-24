package com.vangelnum.testfirebase.feature_main.data.repository

import coil.network.HttpException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_main.domain.model.NewPhotos
import com.vangelnum.testfirebase.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException

class MainRepositoryImpl : MainRepository {
    override suspend fun getAllPhotos(): Flow<Resource<NewPhotos>> = flow {
        try {
            emit(Resource.Loading())
            val myCollection =
                Firebase.firestore.collection("images").document("tutor").get().await()
                    .toObject<NewPhotos>()
            emit(Resource.Success(myCollection))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data"))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error("Couldn't load data"))
        }

    }
}