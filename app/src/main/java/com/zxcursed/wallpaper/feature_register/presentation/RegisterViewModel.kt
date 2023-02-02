package com.zxcursed.wallpaper.feature_register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val registerFlow: StateFlow<Resource<FirebaseUser>?> = _registerFlow


    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _registerFlow.value = Resource.Loading(isLoading = true)
            val result = repository.register(email, password)
            _registerFlow.value = result
        }
    }
}