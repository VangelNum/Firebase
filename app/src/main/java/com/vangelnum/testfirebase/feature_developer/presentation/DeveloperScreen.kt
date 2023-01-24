package com.vangelnum.testfirebase.feature_developer.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vangelnum.testfirebase.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun DeveloperScreen(viewModel: DeveleoperViewModel = hiltViewModel(), auth: FirebaseAuth) {

    val resource = viewModel.allUsersPhotosForDeveloper.value
    UsersImages(auth = auth, allUsersPhotos = resource)

}


@Composable
fun UsersImages(
    auth: FirebaseAuth,
    allUsersPhotos: DeveloperState
) {


    if (allUsersPhotos.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (allUsersPhotos.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Error: ${allUsersPhotos.error}")
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(allUsersPhotos.data) { collectPhotos ->
            Text(text = "Email: ${collectPhotos.email}")
            Text(text = "Uid: ${collectPhotos.userId}")
            Text(text = "Score: ${collectPhotos.score}")
            collectPhotos.url.forEach { onePhoto ->
                Text(text = "Link: $onePhoto")
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = onePhoto,
                        contentDescription = "photos",
                        loading = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(),
                        contentScale = ContentScale.Crop,
                        error = {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_error_24),
                                contentDescription = "error")
                        }
                    )
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()) {
                            IconButton(onClick = {
                                CoroutineScope(Dispatchers.IO).launch {

                                    val myCollection =
                                        Firebase.firestore.collection("images").document("tutor")
                                    val mapUpdate = mapOf(
                                        "arrayImages" to FieldValue.arrayUnion(onePhoto)
                                    )
                                    myCollection.update(mapUpdate).await()

                                    val personCollection = Firebase.firestore.collection("users")
                                        .document(collectPhotos.userId)
                                    personCollection.update(mapOf(
                                        "url" to FieldValue.arrayRemove(onePhoto)
                                    )).await()

                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_upload_24),
                                    contentDescription = "delete",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Red
                                )
                            }
                            IconButton(onClick = {
                                val uid = auth.currentUser?.uid
                                if (uid != null) {
                                    val personCollection =
                                        Firebase.firestore.collection("users")
                                            .document(collectPhotos.userId)
                                    personCollection.update(mapOf(
                                        "url" to FieldValue.arrayRemove(onePhoto)
                                    ))
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                                    contentDescription = "delete",
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
