package com.zxcursed.wallpaper.feature_developer.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos
import com.zxcursed.wallpaper.feature_developer.domain.use_cases.DeveloperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeveleoperViewModel @Inject constructor(
    private val developerUseCase: DeveloperUseCase,
) : ViewModel() {

    private val _allUsersPhotosForDeveloper = mutableStateOf(DeveloperState())
    val allUsersPhotosForDeveloper: State<DeveloperState> = _allUsersPhotosForDeveloper

    init {
        getUsersPhotos()
    }

    private fun getUsersPhotos() {
        viewModelScope.launch {
            developerUseCase.getUsersPhotosUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _allUsersPhotosForDeveloper.value =
                            DeveloperState(error = result.message.toString())
                    }
                    is Resource.Loading -> {
                        _allUsersPhotosForDeveloper.value = DeveloperState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _allUsersPhotosForDeveloper.value =
                            DeveloperState(data = result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun updateUserPhotosDev(onePhoto: String, photos: UserPhotos) {
        viewModelScope.launch(Dispatchers.IO) {
            developerUseCase.addUsersPhotosDevUseCase(onePhoto, photos)
        }
    }

    fun deleteUserPhotosDev(onePhoto: String, photos: UserPhotos) {
        viewModelScope.launch(Dispatchers.IO) {
            developerUseCase.deleteUsersPhotosUseCase(onePhoto, photos)
        }
    }

}