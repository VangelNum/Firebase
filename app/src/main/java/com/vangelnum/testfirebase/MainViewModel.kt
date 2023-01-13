package com.vangelnum.testfirebase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<NewsState>(NewsState.Empty)
    val uiState: StateFlow<NewsState> = _uiState.asStateFlow()

    private val _allPhotos = MutableStateFlow(NewPhotos(emptyList()))
    val allPhotos: StateFlow<NewPhotos> = _allPhotos.asStateFlow()


    private val _allUsersPhotos = MutableStateFlow(UserPhotos(emptyList()))
    val allUsersPhotos: StateFlow<UserPhotos> = _allUsersPhotos.asStateFlow()

    private val myCollection = Firebase.firestore.collection("images").document("tutor")


    fun getUserPhotos() {
        viewModelScope.launch {
            try {
                val userCollection = Firebase.firestore.collection("users").document("")
//                val querySnapShot = userCollection.get().await()
//                for (document in querySnapShot.documents) {
//                    val userPerson = document.toObject<UserPhotos>()
//                    if (userPerson != null) {
//                        _allUsersPhotos.value = userPerson
//                    }
//                }
            } catch (e: Exception) {
                Log.d("Error: ", e.message.toString())
            }
        }
    }

    fun getSomePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = NewsState.Loading
                _allPhotos.value = myCollection.get().await().toObject<NewPhotos>()!!
                _uiState.value = NewsState.Success
            } catch (e: Exception) {
                _uiState.value = NewsState.Error(e.message.toString())
            }
        }
    }

}

sealed class NewsState {
    object Success : NewsState()
    data class Error(val message: String) : NewsState()
    object Loading : NewsState()
    object Empty : NewsState()
}