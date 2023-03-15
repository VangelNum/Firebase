package com.zxcursed.wallpaper.feature_register.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.core.data.Person
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : RegisterRepository {

    override suspend fun register(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun alreadyRegister(): Flow<Resource<FirebaseUser>> = flow {
        if (auth.currentUser != null && auth.currentUser?.isEmailVerified == true) {
            emit(Resource.Success(auth.currentUser))
        } else emit(Resource.Error(""))
    }

    override suspend fun saveData(person: Person) {
        try {
            val collection = fireStore.collection("usersdata")
            collection.add(person).await()
        } catch (e: Exception) {
            Log.d("tag","error ${e.message.toString()}")
            e.printStackTrace()
        }
    }
}