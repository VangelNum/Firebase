package com.vangelnum.testfirebase

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


class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<StatesOfProgress>(StatesOfProgress.Empty)
    val uiState: StateFlow<StatesOfProgress> = _uiState.asStateFlow()

    private val _allPhotos = MutableStateFlow(NewPhotos(emptyList()))
    val allPhotos: StateFlow<NewPhotos> = _allPhotos.asStateFlow()

    private val _uiStateDeveloper = MutableStateFlow<StatesOfProgress>(StatesOfProgress.Empty)
    val uiStateDeveloper: StateFlow<StatesOfProgress> = _uiStateDeveloper.asStateFlow()

    private val _allUsersPhotos = MutableStateFlow(listOf(UserPhotos()))
    val allUsersPhotos: StateFlow<List<UserPhotos>> = _allUsersPhotos.asStateFlow()



    fun getUserPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val personCollection = Firebase.firestore.collection("users")
            personCollection.addSnapshotListener { querySnapshot, firebaseException ->
                firebaseException?.let {
                    // TODO
                    //Toast.makeText(MyApp.getContext(),it.message.toString(),Toast.LENGTH_LONG).show()
                    _uiStateDeveloper.value = StatesOfProgress.Error(it.message.toString())
                }
                querySnapshot?.let {
                    _uiStateDeveloper.value = StatesOfProgress.Loading
                    val userList = mutableListOf<UserPhotos>()
                    for (data in it.documents) {
                        val userPhoto = data.toObject(UserPhotos::class.java)
                        if (!userPhoto?.url?.isEmpty()!!) {
                            userList.add(userPhoto)
                        }

                    }
                    _allUsersPhotos.value = userList
                    _uiStateDeveloper.value = StatesOfProgress.Success
                }
            }
        }
    }

    fun getSomePhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val myCollection = Firebase.firestore.collection("images").document("tutor")
                _uiState.value = StatesOfProgress.Loading
                myCollection.addSnapshotListener { querySnapshot, firebaseException ->
                    firebaseException?.let {
                        // TODO
                        //Toast.makeText(MyApp.getContext(),it.message.toString(),Toast.LENGTH_LONG).show()
                    }
                    querySnapshot?.let { photos ->
                        _allPhotos.value = photos.toObject<NewPhotos>()!!
                    }
                }
                _uiState.value = StatesOfProgress.Success
            } catch (e: Exception) {
                _uiState.value = StatesOfProgress.Error(e.message.toString())
            }
        }
    }

}

sealed class StatesOfProgress {
    object Success : StatesOfProgress()
    data class Error(val message: String) : StatesOfProgress()
    object Loading : StatesOfProgress()
    object Empty : StatesOfProgress()
}