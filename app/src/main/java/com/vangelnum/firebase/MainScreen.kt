package com.vangelnum.firebase

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


data class NewPhotos(
    val arrayimage: List<String> = listOf(),
)

@Composable
fun MainScreen() {
    val myCollection = Firebase.firestore.collection("images").document("tutor")
    var allPhotos by remember {
        mutableStateOf(NewPhotos())
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val result = myCollection.get().await().toObject<NewPhotos>()
                allPhotos = result!!
            }
        }) {
            Text(text = "Get Photos")
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(allPhotos.arrayimage) { currentPhoto ->
                Log.d("check", currentPhoto)
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(10.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    SubcomposeAsyncImage(model = currentPhoto,
                        contentDescription = "photo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}




