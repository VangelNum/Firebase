package com.zxcursed.wallpaper.feature_developer.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.feature_developer.domain.model.UserPhotos
import com.zxcursed.wallpaper.feature_developer.domain.use_cases.DeveloperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeveloperViewModel @Inject constructor(
    private val developerUseCase: DeveloperUseCase,
) : ViewModel() {

    private val _allUsersPhotosForDeveloper = mutableStateOf(DeveloperState())
    val allUsersPhotosForDeveloper: State<DeveloperState> = _allUsersPhotosForDeveloper

    init {
        getUsersPhotos()
    }

    fun getUsersPhotos() {
        developerUseCase.getUsersPhotosUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _allUsersPhotosForDeveloper.value = allUsersPhotosForDeveloper.value.copy(
                        error = result.message.toString()
                    )
                }
                is Resource.Loading -> {
                    _allUsersPhotosForDeveloper.value = allUsersPhotosForDeveloper.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _allUsersPhotosForDeveloper.value = allUsersPhotosForDeveloper.value.copy(
                        isLoading = false,
                        data = result.data ?: emptyList()
                    )

                }
            }
        }.launchIn(viewModelScope)

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

    fun deleteUserPhotoFromFirestore(onePhoto: String, photos: UserPhotos) {
        viewModelScope.launch {
            deleteUserPhotosDev(onePhoto, photos)
            developerUseCase.deleteUsersPhotosFromFirestoreUseCase(onePhoto)
        }
    }

}