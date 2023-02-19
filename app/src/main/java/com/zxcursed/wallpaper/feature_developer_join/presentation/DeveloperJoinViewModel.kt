package com.zxcursed.wallpaper.feature_developer_join.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxcursed.wallpaper.feature_developer_join.domain.DeveloperJoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeveloperJoinViewModel @Inject constructor(
    private val repository: DeveloperJoinRepository
) : ViewModel() {

    private val _developerState = MutableLiveData<Boolean>()
    val developerState: LiveData<Boolean> = _developerState

    init {
        alreadyDeveloper()
    }

    private fun alreadyDeveloper() {
        viewModelScope.launch {
            _developerState.value = repository.getBoolean()
        }
    }

    fun makeDeveloper() {
        viewModelScope.launch {
            repository.putBoolean(true)
        }
    }


}