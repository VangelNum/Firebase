package com.vangelnum.testfirebase.presentation

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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.testfirebase.StatesOfProgress
import com.vangelnum.testfirebase.feature_favourite.presentation.ViewModelForFavourite
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity

@Composable
fun FavouriteScreen(viewModel: ViewModelForFavourite = hiltViewModel()) {

    val uiState = viewModel.uiStateFavourite.collectAsState()
    val allFavouritePhotos = viewModel.allFavouritePhotos.observeAsState()

    when (uiState.value) {
        is StatesOfProgress.Empty -> {
            viewModel.getFavouritePhotos()
        }
        is StatesOfProgress.Error -> {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Something wrong")
                OutlinedButton(onClick = { viewModel.getFavouritePhotos() }) {
                    Text(text = "Try again")
                }
            }
        }
        is StatesOfProgress.Success -> {
            FavouritePhotosLazyGrid(viewModel = viewModel, allFavouritePhotos)
        }
        is StatesOfProgress.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Green)
            }
        }
    }

}

@Composable
fun FavouritePhotosLazyGrid(
    viewModel: ViewModelForFavourite,
    allFavouritePhotos: State<List<FavouritePhotosEntity>?>,
) {

    if (allFavouritePhotos.value?.isEmpty() == false) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            items(allFavouritePhotos.value!!) { photo ->
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
                               // viewModel.deleteFavouritePhotoUrl(url = photo.url)
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