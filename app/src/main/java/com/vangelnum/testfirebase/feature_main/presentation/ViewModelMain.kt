package com.vangelnum.testfirebase.feature_main.presentation

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
        getAllPhotosUseCase().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _allPhotos.value =
                        AllPhotosState(error = result.message ?: "An unexpected error")
                }
                is Resource.Success -> {
                    _allPhotos.value = AllPhotosState(data = result.data)
                }
                is Resource.Loading -> {
                    _allPhotos.value = AllPhotosState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}