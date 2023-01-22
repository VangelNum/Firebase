package com.vangelnum.testfirebase.feature_developer.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vangelnum.testfirebase.common.Resource
import com.vangelnum.testfirebase.feature_developer.domain.use_cases.GetUsersPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DeveleoperViewModel @Inject constructor(
    private val getUsersPhotosUseCase: GetUsersPhotosUseCase
): ViewModel() {

    private val _allUsersPhotosForDeveloper = mutableStateOf(DeveloperState())
    val allUsersPhotosForDeveloper: State<DeveloperState> = _allUsersPhotosForDeveloper

    init {
        getUsersPhotos()
    }

    private fun getUsersPhotos() {
        getUsersPhotosUseCase().onEach { result->
            when(result) {
                is Resource.Error -> {
                    _allUsersPhotosForDeveloper.value =  DeveloperState(error = result.message.toString())
                }
                is Resource.Loading -> {
                    _allUsersPhotosForDeveloper.value = DeveloperState(isLoading = true)
                }
                is Resource.Success -> {
                    _allUsersPhotosForDeveloper.value = DeveloperState(data = result.data?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}