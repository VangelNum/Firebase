package com.zxcursed.wallpaper.feature_main.data.repository

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_main.domain.model.NewPhotos
import com.zxcursed.wallpaper.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : MainRepository {
    override suspend fun getAllPhotos(): Flow<Resource<NewPhotos>> = flow {
        try {
            emit(Resource.Loading())
            val myCollection =
                fireStore
                    .collection("images")
                    .document("tutor")
                    .get()
                    .await()
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