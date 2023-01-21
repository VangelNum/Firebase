package com.vangelnum.testfirebase.feature_favourite.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.testfirebase.StatesOfProgress
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import com.vangelnum.testfirebase.feature_favourite.domain.repository.FavouritePhotosRepository
import com.vangelnum.testfirebase.feature_favourite.domain.use_case.AddToFavouriteUseCase
import com.vangelnum.testfirebase.feature_favourite.domain.use_case.GetFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelForFavourite @Inject constructor(
    private val getFavourite: GetFavouriteUseCase,
    private val addFavourite: AddToFavouriteUseCase
): ViewModel() {

    private val _allFavouritePhotos: MutableLiveData<List<FavouritePhotosEntity>> = MutableLiveData()
    val allFavouritePhotos: LiveData<List<FavouritePhotosEntity>> = _allFavouritePhotos

    private val _uiStateFavourite = MutableStateFlow<StatesOfProgress>(StatesOfProgress.Empty)
    val uiStateFavourite: StateFlow<StatesOfProgress> = _uiStateFavourite.asStateFlow()

    fun getFavouritePhotos() {
        _uiStateFavourite.value = StatesOfProgress.Loading
        viewModelScope.launch {
            try {
                getFavourite().collect { result ->
                    _allFavouritePhotos.value = result
                    _uiStateFavourite.value = StatesOfProgress.Success
                }
            } catch (e: Exception) {
                Log.d("tag",e.message.toString())
            }
        }
    }

    fun addFavouritePhoto(photo: FavouritePhotosEntity) {
        viewModelScope.launch {
            try {
                addFavourite(photo)
            } catch (e: Exception) {
                Log.d("tag",e.message.toString())
            }
        }
    }


    //    private val _allFavouritePhotos: MutableLiveData<List<FavouritePhotosEntity>> = MutableLiveData()
//    val allFavouritePhotos: LiveData<List<FavouritePhotosEntity>> = _allFavouritePhotos
//

//    fun getFavouritePhotos() {
//        viewModelScope.launch {
//            try {
//                _uiStateFavourite.value = StatesOfProgress.Loading
//                repositoryFavourite.getAllPhotos().collect { response->
//                    _allFavouritePhotos.postValue(response)
//                    _uiStateFavourite.value = StatesOfProgress.Success
//                }
//            } catch (e: Exception) {
//                _uiStateFavourite.value = StatesOfProgress.Error(e.message.toString())
//            }
//        }
//    }

//    fun addFavouritePhoto(photo: FavouritePhotosEntity) {
//        viewModelScope.launch {
//            repositoryFavourite.addPhoto(photo)
//        }
//    }
//
//    fun deleteFavouritePhotoUrl(url: String) {
//        viewModelScope.launch {
//            repositoryFavourite.deletePhotoUrl(url)
//        }
//    }
}