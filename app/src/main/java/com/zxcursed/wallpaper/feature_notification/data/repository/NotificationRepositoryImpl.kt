package com.zxcursed.wallpaper.feature_notification.data.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_notification.domain.model.NotificationToUserData
import com.zxcursed.wallpaper.feature_notification.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class NotificationRepositoryImpl : NotificationRepository {
    override suspend fun getNotifications(): Flow<Resource<NotificationToUserData>> = flow {
        try {
            emit(Resource.Loading())
            val auth = Firebase.auth
            val uid = auth.currentUser?.uid.toString()
            val collection = Firebase.firestore.collection("users").document(uid)
            val querySnapShot = collection.get().await()
            val state = querySnapShot.toObject<NotificationToUserData>()

            Log.d("tag",state.toString())

            emit(Resource.Success(state))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}