package com.zxcursed.wallpaper.feature_main.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import com.zxcursed.wallpaper.feature_favourite.presentation.ViewModelForFavourite
import com.zxcursed.wallpaper.presentation.Screens
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalFoundationApi::class)
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

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = resources.isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModelMain.getAllPhotos() },
        indicator = { state, refreshTrigger ->
            val isLoading = resources.isLoading
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                contentColor = if (isLoading) Color.Transparent else Color.Green,
                backgroundColor = if (isLoading) Color.Transparent else MaterialTheme.colors.surface
            )
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            resources.data?.let {
                items(it.arrayImages) { photoUrl ->
                    Card(
                        modifier = Modifier
                            .clickable {
                                val encodedUrl = URLEncoder.encode(
                                    photoUrl,
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate(Screens.WatchPhoto.withArgs(encodedUrl))
                            },
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        SubcomposeAsyncImage(
                            model = photoUrl,
                            contentDescription = "photo",
                            contentScale = ContentScale.FillWidth,
                        ) {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                        Box(
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
                                        val result =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                "Удалено из избранного",
                                                "Отмена",
                                                duration = SnackbarDuration.Short
                                            )
                                        when (result) {
                                            SnackbarResult.ActionPerformed -> {
                                                viewModelForFavourite.addFavouritePhoto(
                                                    FavouritePhotosEntity(url = photoUrl)
                                                )
                                            }
                                            else -> Unit
                                        }
                                    }
                                } else {
                                    viewModelForFavourite.addFavouritePhoto(
                                        FavouritePhotosEntity(
                                            url = photoUrl
                                        )
                                    )
                                    scope.launch {
                                        val result = scaffoldState
                                            .snackbarHostState
                                            .showSnackbar(
                                                "Добавлено в избранное",
                                                "Отмена",
                                                duration = SnackbarDuration.Short
                                            )
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
                                    tint = if (photoInFavourite.value) Color.Red else Color.White,
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




