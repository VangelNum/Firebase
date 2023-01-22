package com.vangelnum.testfirebase.feature_main.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.testfirebase.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MainScreen(
    viewModelMain: ViewModelMain = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    val resources = viewModelMain.allPhotos.value

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        ) {
            resources.data?.let { photos ->
                items(photos.arrayImages) { photoUrl ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clickable {
                            val encodedUrl =
                                URLEncoder.encode(photoUrl, StandardCharsets.UTF_8.toString())
                            navController.navigate(Screens.WatchPhoto.withArgs(encodedUrl))
                        },
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        SubcomposeAsyncImage(model = photoUrl,
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
        if (resources.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = resources.error, color = Color.Red)
            }
        }
        if (resources.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

