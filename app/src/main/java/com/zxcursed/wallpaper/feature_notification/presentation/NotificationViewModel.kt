package com.zxcursed.wallpaper.feature_notification.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.feature_notification.domain.model.NotificationToUserData
import com.zxcursed.wallpaper.feature_notification.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _allNotifications = mutableStateOf(NotificationStates())
    val allNotifications: State<NotificationStates> = _allNotifications

    init {
        getAllNotifications()
    }

    fun getAllNotifications() {
        viewModelScope.launch {
            repository.getNotifications().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _allNotifications.value = NotificationStates(
                            isLoading = false, data = result.data ?: NotificationToUserData(
                                emptyList()
                            )
                        )
                    }
                    is Resource.Error -> {
                        _allNotifications.value =
                            NotificationStates(error = result.message.toString())
                    }
                    is Resource.Loading -> {
                        _allNotifications.value = NotificationStates(isLoading = true)
                    }
                }
            }
        }
    }


}