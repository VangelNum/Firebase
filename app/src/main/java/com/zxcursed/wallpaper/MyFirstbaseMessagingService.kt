package com.zxcursed.wallpaper

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyFirstbaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null) {
            Log.d("tag", "${message.notification!!.title}")
        } else {
            Log.d("tag", "its null")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        GlobalScope.launch {
            saveToken(token)
        }
    }

    //save token
    private suspend fun saveToken(token: String) {
        val gckTokenKey = stringPreferencesKey("gcm_token")
        baseContext.dataStore.edit { pref ->
            pref[gckTokenKey] = token
        }
    }
}