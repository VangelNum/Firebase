package com.vangelnum.testfirebase

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth



@Composable
fun MainScreen(viewModel: MainViewModel, auth: FirebaseAuth, navHostController: NavHostController) {

    val uiState = viewModel.uiState.collectAsState()
    val allPhotos = viewModel.allPhotos.collectAsState()
    val context = LocalContext.current

    when (uiState.value) {
        is NewsState.Error -> {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Something wrong")
                OutlinedButton(onClick = { viewModel.getSomePhotos() }) {
                    Text(text = "Try again")
                }
            }
        }
        is NewsState.Success -> {
            ColumnImages(allPhotos.value, auth, navHostController)
        }
        is NewsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Green)
            }
        }
        is NewsState.Empty -> {
            viewModel.getSomePhotos()
        }
    }
}


@Composable
fun ColumnImages(allPhotos: NewPhotos, auth: FirebaseAuth, navHostController: NavHostController) {

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(allPhotos.arrayImages) { currentPhoto ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                SubcomposeAsyncImage(model = currentPhoto,
                    contentDescription = "photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                )
            }
        }
    }
}



