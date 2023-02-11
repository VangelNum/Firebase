package com.zxcursed.wallpaper.feature_add_photo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxcursed.wallpaper.feature_add_photo.domain.AddPhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPhotoViewModel @Inject constructor(
    private val repository: AddPhotoRepository
): ViewModel() {
    fun addPhoto(textValue: String) {
        viewModelScope.launch {
            repository.addPhoto(textValue)
        }
    }
}