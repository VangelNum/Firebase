package com.vangelnum.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vangelnum.firebase.ui.Screens
import com.vangelnum.firebase.ui.theme.FirebaseTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
                MyAppNavHost()
            }
        }
    }
}
