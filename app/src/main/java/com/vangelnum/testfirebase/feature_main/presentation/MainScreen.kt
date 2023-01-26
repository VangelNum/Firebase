package com.vangelnum.testfirebase.feature_main.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.vangelnum.testfirebase.R
import com.vangelnum.testfirebase.Screens
import com.vangelnum.testfirebase.feature_favourite.domain.model.FavouritePhotosEntity
import com.vangelnum.testfirebase.feature_favourite.presentation.ViewModelForFavourite
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MainScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    viewModelMain: ViewModelMain = hiltViewModel(),
    viewModelForFavourite: ViewModelForFavourite = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()
    val resources = viewModelMain.allPhotos.value
    val favourites = viewModelForFavourite.allFavouritePhotos.value

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
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.BottomEnd
                        ) {

                            val photoInFavourite = remember {
                                mutableStateOf(false)
                            }

                            photoInFavourite.value = favourites.data.toString().contains(photoUrl)
                            IconButton(onClick = {
                                if (photoInFavourite.value) {
                                    viewModelForFavourite.deleteFavouritePhoto(photoUrl)
                                    scope.launch {
                                        val result = scaffoldState.snackbarHostState.showSnackbar("Удалено из избранного",
                                                "Отмена")
                                        when (result) {
                                            SnackbarResult.ActionPerformed -> {
                                                viewModelForFavourite.addFavouritePhoto(
                                                    FavouritePhotosEntity(url = photoUrl))
                                            }
                                            else -> Unit
                                        }
                                    }
                                } else {
                                    viewModelForFavourite.addFavouritePhoto(FavouritePhotosEntity(
                                        url = photoUrl))
                                    scope.launch {
                                        val result = scaffoldState
                                            .snackbarHostState
                                            .showSnackbar("Добавлено в избранное",
                                                "Отмена",
                                                duration = SnackbarDuration.Short)
                                        Log.d("tag",result.toString())
                                        when (result) {
                                            SnackbarResult.ActionPerformed -> {
                                                viewModelForFavourite.deleteFavouritePhoto(url = photoUrl)
                                            }
                                            else -> Unit
                                        }
                                    }
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_round_favorite_24),
                                    contentDescription = "favourite",
                                    tint = if (photoInFavourite.value) Color.Red else Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
        if (resources.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = resources.error, color = Color.Red)
            }
        }
        if (resources.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

}


