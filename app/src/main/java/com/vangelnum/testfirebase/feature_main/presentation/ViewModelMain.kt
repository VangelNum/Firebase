package com.vangelnum.testfirebase.feature_main.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.testfirebase.feature_favourite.common.Resource
import com.vangelnum.testfirebase.feature_main.domain.use_cases.GetAllPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ViewModelMain @Inject constructor(
    private val getAllPhotosUseCase: GetAllPhotosUseCase,
) : ViewModel() {

    private val _allPhotos = mutableStateOf(AllPhotosState())
    val allPhotos: State<AllPhotosState> = _allPhotos

    init {
        getAllPhotos()
    }

    private fun getAllPhotos() {
        getAllPhotosUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _allPhotos.value = AllPhotosState(isLoading = true)
                }
                is Resource.Error -> {
                    _allPhotos.value = AllPhotosState(error = it.message.toString())
                }
                is Resource.Success -> {
                    _allPhotos.value = AllPhotosState(data = it.data!!)
                }
            }
        }.launchIn(viewModelScope)
    }

//    private val _allPhotos = MutableStateFlow(NewPhotos(emptyList()))
//    val allPhotos: StateFlow<NewPhotos> = _allPhotos.asStateFlow()
//
//
//    fun getSomePhotos() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val myCollection = Firebase.firestore.collection("images").document("tutor")
//                //_uiState.value = StatesOfProgress.Loading
//                myCollection.addSnapshotListener { querySnapshot, firebaseException ->
//                    firebaseException?.let {
//                        // TODO
//                        //Toast.makeText(MyApp.getContext(),it.message.toString(),Toast.LENGTH_LONG).show()
//                    }
//                    querySnapshot?.let { photos ->
//                        _allPhotos.value = photos.toObject<NewPhotos>()!!
//                    }
//                }
//                //_uiState.value = StatesOfProgress.Success
//            } catch (e: Exception) {
//                //_uiState.value = StatesOfProgress.Error(e.message.toString())
//            }
//        }
//    }
}