package com.zxcursed.wallpaper.feature_register.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.zxcursed.wallpaper.common.Resource
import com.zxcursed.wallpaper.feature_register.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _registerFlow = mutableStateOf(RegisterState())
    val registerFlow: State<RegisterState> = _registerFlow



    init {
        alreadyRegister()
    }

    private fun alreadyRegister() {
        _registerFlow.value = RegisterState(isLoading = true)
        val response = auth.currentUser
        if (response != null && response.isEmailVerified) {
            _registerFlow.value = RegisterState(data = response)
        } else {
            _registerFlow.value = RegisterState(isLoading = false)
        }
    }

    fun registerUser(email: String, password: String) {
        repository.register(email, password).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _registerFlow.value = RegisterState(isLoading = true)
                }
                is Resource.Error -> {
                    _registerFlow.value = RegisterState(
                        error = resource.message.toString(),
                        isLoading = false
                    )
                }
                is Resource.Success -> {
                    _registerFlow.value = RegisterState(
                        data = resource.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)

    }
}