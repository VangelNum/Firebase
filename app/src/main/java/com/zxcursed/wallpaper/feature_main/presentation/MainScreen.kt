package com.zxcursed.wallpaper.feature_main.presentation


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.core.presentation.navigation.Screens
import com.zxcursed.wallpaper.feature_favourite.domain.model.FavouritePhotosEntity
import com.zxcursed.wallpaper.feature_favourite.presentation.ViewModelForFavourite
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

    val listState = rememberLazyStaggeredGridState()
    val isVisible = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val context = LocalContext.current

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModelMain.getAllPhotos() },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                contentColor = Color.Green,
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) {

        LazyVerticalStaggeredGrid(
            state = listState,
            columns = StaggeredGridCells.Adaptive(128.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp),
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
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> {
                                    Box(
                                        modifier = Modifier
                                            .height(200.dp)
                                            .placeholder(
                                                visible = true,
                                                color = Color.Gray,
                                                highlight = PlaceholderHighlight.shimmer(
                                                    highlightColor = Color.White,
                                                ),
                                            )
                                    )
                                }

                                is AsyncImagePainter.State.Error -> {
                                    Box(
                                        modifier = Modifier.height(200.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.error_loading_image),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                else -> {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }

                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {

                            val photoInFavourite = remember {
                                mutableStateOf(false)
                            }
                            photoInFavourite.value =
                                favourites.data.toString().contains(photoUrl)
                            IconButton(onClick = {
                                if (photoInFavourite.value) {
                                    viewModelForFavourite.deleteFavouritePhoto(photoUrl)
                                    scope.launch {
                                        val result =
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                context.getString(R.string.deleted_from_favourite),
                                                context.getString(R.string.cancel),
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
                                                context.getString(R.string.added_to_favourite),
                                                context.getString(R.string.cancel),
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

        AnimatedVisibility(
            visible = isVisible.value,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(durationMillis = 500)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(index = 0)
                        }
                    },
                    backgroundColor = MaterialTheme.colors.surface
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_upward_24),
                        contentDescription = "up"
                    )
                }
            }
        }
    }
    if (resources.error.isNotBlank())
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = resources.error)
                OutlinedButton(
                    onClick = { viewModelMain.getAllPhotos() },
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Text(
                        text = stringResource(id = R.string.reload),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                        )
                    )
                }
            }
        }

}
