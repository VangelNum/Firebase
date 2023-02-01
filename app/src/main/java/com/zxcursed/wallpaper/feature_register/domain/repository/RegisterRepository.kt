package com.zxcursed.wallpaper.feature_register.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {


    fun register(email: String, password: String) : Flow<Resource<FirebaseUser>>
}