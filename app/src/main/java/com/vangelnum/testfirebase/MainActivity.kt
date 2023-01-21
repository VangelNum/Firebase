package com.vangelnum.testfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.vangelnum.testfirebase.feature_main.presentation.MainViewModel
import com.vangelnum.testfirebase.presentation.Navigation
import com.vangelnum.testfirebase.ui.theme.FirebaseTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            FirebaseTheme {
                val navController = rememberNavController()
                Navigation(navController, viewModel)
            }
        }
    }
}
