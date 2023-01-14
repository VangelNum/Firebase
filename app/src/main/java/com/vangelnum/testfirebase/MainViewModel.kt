package com.vangelnum.testfirebase

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<StatesOfProgress>(StatesOfProgress.Empty)
    val uiState: StateFlow<StatesOfProgress> = _uiState.asStateFlow()

    private val _allPhotos = MutableStateFlow(NewPhotos(emptyList()))
    val allPhotos: StateFlow<NewPhotos> = _allPhotos.asStateFlow()


    private val _uiStateDeveloper = MutableStateFlow<StatesOfProgress>(StatesOfProgress.Empty)
    val uiStateDeveloper: StateFlow<StatesOfProgress> = _uiStateDeveloper.asStateFlow()

    private val _allUsersPhotos = MutableStateFlow(listOf(UserPhotos()))
    val allUsersPhotos: StateFlow<List<UserPhotos>> = _allUsersPhotos.asStateFlow()

    private val myCollection = Firebase.firestore.collection("images").document("tutor")


    fun getUsersPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userList = mutableListOf<UserPhotos>()
                _uiStateDeveloper.value = StatesOfProgress.Loading
                Firebase.firestore.collection("users").get().addOnSuccessListener {
                    if (!it.isEmpty) {
                        for (data in it.documents) {
                            val userPhoto = data.toObject(UserPhotos::class.java)
                            userList.add(userPhoto!!)
                        }
                        _allUsersPhotos.value = userList
                        _uiStateDeveloper.value = StatesOfProgress.Success
                    } else {
                        _uiStateDeveloper.value = StatesOfProgress.Error("Empty")
                    }
                }
            } catch (e: Exception) {
                _uiStateDeveloper.value = StatesOfProgress.Error(e.message.toString())
                Log.d("Error: ", e.message.toString())
            }
        }
    }

    fun getUserPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val personCollection = Firebase.firestore.collection("users")
            personCollection.addSnapshotListener {  querySnapshot, firebaseException ->
                firebaseException?.let {
                    Toast.makeText(MyApp.getContext(),it.message.toString(),Toast.LENGTH_LONG).show()
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
                _uiState.value = StatesOfProgress.Loading
                myCollection.addSnapshotListener { querySnapshot, firebaseException ->
                    firebaseException?.let {
                        Toast.makeText(MyApp.getContext(),it.message.toString(),Toast.LENGTH_LONG).show()
                    }
                    querySnapshot?.let { photos->
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