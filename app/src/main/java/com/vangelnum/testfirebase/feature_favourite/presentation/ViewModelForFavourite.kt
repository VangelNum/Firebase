package com.vangelnum.testfirebase.feature_favourite.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.testfirebase.feature_favourite.common.Resource
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import com.vangelnum.testfirebase.feature_favourite.domain.use_case.AddToFavouriteUseCase
import com.vangelnum.testfirebase.feature_favourite.domain.use_case.DeleteFavouriteUseCase
import com.vangelnum.testfirebase.feature_favourite.domain.use_case.GetFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelForFavourite @Inject constructor(
    private val getFavouritePhotosUseCase: GetFavouriteUseCase,
    private val addFavouriteUseCase: AddToFavouriteUseCase,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCase,
) : ViewModel() {


    private val _allFavouritePhotos = mutableStateOf(FavouriteState())
    val allFavouritePhotos: State<FavouriteState> = _allFavouritePhotos

    init {
        getFavouritePhotos()
    }

    fun getFavouritePhotos() {
        getFavouritePhotosUseCase().onEach { resources ->
            when (resources) {
                is Resource.Loading -> {
                    _allFavouritePhotos.value = FavouriteState(isLoading = true)
                }
                is Resource.Error -> {
                    _allFavouritePhotos.value = FavouriteState(error = resources.message.toString())
                }
                is Resource.Success -> {
                    _allFavouritePhotos.value = FavouriteState(data = resources.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addFavouritePhoto(photo: FavouritePhotosEntity) {
        viewModelScope.launch {
            try {
                addFavouriteUseCase(photo)
            } catch (e: Exception) {
                Log.d("tag", e.message.toString())
            }
        }
    }

    fun deleteFavouritePhoto(url: String) {
        viewModelScope.launch {
            try {
                deleteFavouriteUseCase(url)
            } catch (e: Exception) {
                Log.d("tag", e.message.toString())
            }
        }
    }
}