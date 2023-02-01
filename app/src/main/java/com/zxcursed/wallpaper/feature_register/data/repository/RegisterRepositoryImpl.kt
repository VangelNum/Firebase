package com.zxcursed.wallpaper.feature_register.data.repository

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : RegisterRepository {

    override fun register(email: String, password: String): Flow<Resource<FirebaseUser>> = flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                auth.currentUser?.sendEmailVerification()
                emit(Resource.Success(result.user))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
}