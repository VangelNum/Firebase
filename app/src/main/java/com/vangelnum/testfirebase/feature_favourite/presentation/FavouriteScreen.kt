package com.vangelnum.testfirebase.feature_favourite.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity

@Composable
fun FavouriteScreen(viewModel: ViewModelForFavourite = hiltViewModel()) {

    val resource = viewModel.allFavouritePhotos.value

    if (resource.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Green)
        }
    }
    if (resource.error.isNotBlank()) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = resource.error)
            OutlinedButton(onClick = { viewModel.getFavouritePhotos() }) {
                Text(text = "Try again")
            }
        }
    }
    FavouritePhotosLazyGrid(viewModel = viewModel, resource.data)

}

@Composable
fun FavouritePhotosLazyGrid(
    viewModel: ViewModelForFavourite,
    allFavouritePhotos: List<FavouritePhotosEntity>?,
) {

    if (allFavouritePhotos?.isEmpty() == false) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            items(allFavouritePhotos) { photo ->
                Card(
                    modifier = Modifier.height(350.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = photo.url,
                        contentDescription = "photo",
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth()
                            .clickable {
                                viewModel.deleteFavouritePhoto(url = photo.url)
                            },
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
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Список пуст")
        }
    }
}