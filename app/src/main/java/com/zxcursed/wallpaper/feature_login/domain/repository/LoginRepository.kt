package com.zxcursed.wallpaper.feature_login.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.core.data.Person

interface LoginRepository {
    suspend fun login(email: String,password: String) : Resource<FirebaseUser>
    suspend fun saveData(person: Person)
}