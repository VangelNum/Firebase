package com.vangelnum.testfirebase.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.testfirebase.MainViewModel
import com.vangelnum.testfirebase.NewPhotos
import com.vangelnum.testfirebase.Screens
import com.vangelnum.testfirebase.StatesOfProgress
import com.vangelnum.testfirebase.room.FavouritePhotosEntity
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {

    val uiState = viewModel.uiState.collectAsState()
    val allPhotos = viewModel.allPhotos.collectAsState()
    val context = LocalContext.current

    when (uiState.value) {
        is StatesOfProgress.Error -> {
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
        is StatesOfProgress.Success -> {
            ColumnImages(allPhotos.value, viewModel = viewModel, navController)
        }
        is StatesOfProgress.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Green)
            }
        }
        is StatesOfProgress.Empty -> {
            viewModel.getSomePhotos()
        }
    }
}


@Composable
fun ColumnImages(allPhotos: NewPhotos, viewModel: MainViewModel, navController: NavController) {

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
    ) {
        items(allPhotos.arrayImages) { currentPhoto ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(350.dp).clickable {
                    val encodedUrl = URLEncoder.encode(currentPhoto, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screens.WatchPhoto.withArgs(encodedUrl))
                },
                shape = RoundedCornerShape(25.dp)
            ) {
                SubcomposeAsyncImage(model = currentPhoto,
                    contentDescription = "photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
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



