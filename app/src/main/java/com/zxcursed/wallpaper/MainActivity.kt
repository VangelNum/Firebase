package com.zxcursed.wallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zxcursed.wallpaper.core.presentation.navigation.Navigation
import com.zxcursed.wallpaper.ui.theme.FirebaseTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
                Navigation()
            }
        }
    }
}
