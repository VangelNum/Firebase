package com.zxcursed.wallpaper.feature_register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val registerFlow: StateFlow<Resource<FirebaseUser>?> = _registerFlow.asStateFlow()

    private val _alreadyRegisterFlow = MutableStateFlow<Resource<FirebaseUser>>(Resource.Loading())
    val alreadyRegisterFlow: StateFlow<Resource<FirebaseUser>> = _alreadyRegisterFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _alreadyRegisterFlow.value = Resource.Loading()
            delay(500L)
            repository.alreadyRegister().collect {
               _alreadyRegisterFlow.value = it
            }
        }
    }


    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            _registerFlow.value = Resource.Loading()
            val result = repository.register(email, password)
            _registerFlow.value = result
        }
    }
}