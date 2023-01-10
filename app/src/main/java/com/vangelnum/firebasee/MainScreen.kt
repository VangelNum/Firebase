package com.vangelnum.firebasee

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.FirebaseAuth


data class NewPhotos(
    val arrayimage: List<String> = listOf(),
)

@Composable
fun MainScreen(viewModel: MainViewModel, auth: FirebaseAuth) {
    val uiState = viewModel.uiState.collectAsState()
    val allPhotos = viewModel.allPhotos.collectAsState()
    val context = LocalContext.current

    when (uiState.value) {
        is NewsState.Error -> {
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }
        is NewsState.Success -> {
            ColumnImages(allPhotos.value, auth)
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
fun ColumnImages(allPhotos: NewPhotos, auth: FirebaseAuth) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(allPhotos.arrayimage) { currentPhoto ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
                shape = RoundedCornerShape(25.dp)
            ) {
                SubcomposeAsyncImage(model = currentPhoto,
                    contentDescription = "photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
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
    Button(onClick = { }) {

    }
}



