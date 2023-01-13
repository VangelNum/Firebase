package com.vangelnum.testfirebase

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun DeveloperScreen(viewModel: MainViewModel) {
    val allUsersPhotos = viewModel.allUsersPhotos.collectAsState()
    viewModel.getUserPhotos()

    Log.d("tag",allUsersPhotos.value.toString())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(allUsersPhotos.value.url) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)) {
                SubcomposeAsyncImage(
                    model = it,
                    contentDescription = "photos",
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()){
                            CircularProgressIndicator()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(400.dp)
                )
            }
        }
    }

}