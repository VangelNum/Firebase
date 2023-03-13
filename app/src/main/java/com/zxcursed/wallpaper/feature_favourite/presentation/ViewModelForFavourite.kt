package com.zxcursed.wallpaper.feature_favourite.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import com.zxcursed.wallpaper.feature_favourite.domain.use_case.AddToFavouriteUseCase
import com.zxcursed.wallpaper.feature_favourite.domain.use_case.DeleteFavouriteUseCase
import com.zxcursed.wallpaper.feature_favourite.domain.use_case.GetFavouriteUseCase
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
                    _allFavouritePhotos.value = allFavouritePhotos.value.copy(
                        isLoading = true
                    )
                }

                is Resource.Error -> {
                    _allFavouritePhotos.value = allFavouritePhotos.value.copy(
                        error = resources.message.toString(),
                        isLoading = false
                    )
                }

                is Resource.Success -> {
                    _allFavouritePhotos.value = allFavouritePhotos.value.copy(
                        data = resources.data ?: emptyList(),
                        isLoading = false
                    )
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