package com.zxcursed.wallpaper.feature_developer.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_developer.data.dto.UserPhotosDto
import com.zxcursed.wallpaper.feature_developer.data.mapper.toUserPhotos
import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos
import com.zxcursed.wallpaper.feature_developer.domain.repository.DeveloperRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DeveloperRepositoryImpl : DeveloperRepository {
    override fun getUsersPhotos(): Flow<Resource<List<UserPhotos>>> = callbackFlow {
        Resource.Loading<Boolean>(isLoading = true)
        try {
            val personCollection =
                Firebase.firestore.collection("users").addSnapshotListener { snapshot, error ->
                    val response = snapshot?.let {
                        val userPhotoDto = it.toObjects<UserPhotosDto>().map { userPhotosDto ->
                            userPhotosDto.toUserPhotos()
                        }
                        Resource.Success(data = userPhotoDto)
                    }
                    error.let {
                        Resource.Error(message = it?.message.toString(), data = null)
                    }
                    if (response != null) {
                        trySend(response).isSuccess
                    }
                }
            awaitClose {
                personCollection.remove()
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString(), data = null)
        }

    }

    override suspend fun addUsersPhotosFromDeveloper(
        onePhoto: String,
        collectPhotos: UserPhotos,
    ) {

        val myCollection =
            Firebase.firestore.collection("images")
                .document("tutor")
        val mapUpdate = mapOf(
            "arrayImages" to FieldValue.arrayUnion(onePhoto)
        )

        myCollection.update(mapUpdate).await()

        val personCollection = Firebase.firestore.collection("users").document(collectPhotos.userId)
        personCollection.update(
            mapOf(
                "url" to FieldValue.arrayRemove(onePhoto)
            )
        ).await()


        val photo = mutableMapOf(
            "name1" to onePhoto,
            "name2" to "gray"
        )

        val notifCollection = Firebase.firestore.collection("users")

        notifCollection.whereArrayContains("notification", photo).get()
            .addOnCompleteListener { task ->
                task.apply {
                    if (task.isSuccessful) {
                        for (document in result) {
                            val docId = notifCollection.document(collectPhotos.userId)
                            docId.update("notification", FieldValue.arrayRemove(photo))
                                .addOnCompleteListener { removeTask ->
                                    if (removeTask.isSuccessful) {
                                        docId.update(
                                            "notification",
                                            FieldValue.arrayUnion(
                                                mutableMapOf(
                                                    "name1" to onePhoto,
                                                    "name2" to "green"
                                                )
                                            )
                                        ).addOnCompleteListener { additionTask ->
                                            if (additionTask.isSuccessful) {
                                                Log.d("tag", "Update complete")
                                            } else {
                                                additionTask.exception?.message?.let {
                                                    Log.e("tag", it)
                                                }
                                            }
                                        }
                                    } else {
                                        removeTask.exception?.message?.let {
                                            Log.e("tag", it)
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.message?.let {
                            Log.e("tag", it)
                        }
                    }
                }
            }


    }

    override suspend fun deleteUsersPhotosFromDeveleoper(
        onePhoto: String,
        collectPhotos: UserPhotos,
    ) {

        val personCollection =
            Firebase.firestore.collection("users")
                .document(collectPhotos.userId)
        personCollection.update(
            mapOf(
                "url" to FieldValue.arrayRemove(onePhoto)
            )
        )


        val photo = mutableMapOf(
            "name1" to onePhoto,
            "name2" to "gray"
        )

        val notifCollection = Firebase.firestore.collection("users")

        notifCollection.whereArrayContains("notification", photo).get()
            .addOnCompleteListener { task ->
                task.apply {
                    if (task.isSuccessful) {
                        for (document in result) {
                            val docId = notifCollection.document(collectPhotos.userId)
                            docId.update("notification", FieldValue.arrayRemove(photo))
                                .addOnCompleteListener { removeTask ->
                                    if (removeTask.isSuccessful) {
                                        docId.update(
                                            "notification",
                                            FieldValue.arrayUnion(
                                                mutableMapOf(
                                                    "name1" to onePhoto,
                                                    "name2" to "red"
                                                )
                                            )
                                        ).addOnCompleteListener { additionTask ->
                                            if (additionTask.isSuccessful) {
                                                Log.d("tag", "Update complete")
                                            } else {
                                                additionTask.exception?.message?.let {
                                                    Log.e("tag", it)
                                                }
                                            }
                                        }
                                    } else {
                                        removeTask.exception?.message?.let {
                                            Log.e("tag", it)
                                        }
                                    }
                                }
                        }
                    } else {
                        task.exception?.message?.let {
                            Log.e("tag", it)
                        }
                    }
                }
            }


    }
}