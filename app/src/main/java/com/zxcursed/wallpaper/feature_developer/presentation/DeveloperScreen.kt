package com.zxcursed.wallpaper.feature_developer.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.presentation.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun DeveloperScreen(navController: NavController, viewModel: DeveloperViewModel = hiltViewModel()) {

    val resource = viewModel.allUsersPhotosForDeveloper.value
    UsersImages(viewModel, allUsersPhotos = resource, navController)

}


@Composable
fun UsersImages(
    viewModel: DeveloperViewModel,
    allUsersPhotos: DeveloperState,
    navController: NavController
) {


    if (allUsersPhotos.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    if (allUsersPhotos.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column() {
                Text(text = allUsersPhotos.error)
                Button(onClick = { viewModel.getUsersPhotos() }) {
                    Text(text = stringResource(id = R.string.reload))
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
    ) {
        items(allUsersPhotos.data) { collectPhotos ->
            if (collectPhotos.url.isNotEmpty()) {
                collectPhotos.url.forEach { onePhoto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clickable {
                                val encodedUrl = URLEncoder.encode(
                                    onePhoto,
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate(Screens.WatchPhoto.withArgs(encodedUrl))
                            },
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = onePhoto,
                            contentDescription = "photos",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_error_24),
                                        contentDescription = "error",
                                    )
                                }
                            },
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            viewModel.updateUserPhotosDev(onePhoto, collectPhotos)
                        }, colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.height(60.dp),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.upload),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            if (onePhoto.contains("https://firebasestorage")) {
                                viewModel.deleteUserPhotoFromFirestore(onePhoto, collectPhotos)
                            } else {
                                viewModel.deleteUserPhotosDev(onePhoto, collectPhotos)
                            }
                        }, colors = ButtonDefaults.buttonColors(Color.Transparent),
                        modifier = Modifier.height(60.dp),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = stringResource(id = R.string.link) + " ")
                    SelectionContainer() {
                        Text(text = onePhoto)
                    }


                    Text(text = stringResource(id = R.string.email) + " " + collectPhotos.email)
                    Text(text = stringResource(id = R.string.uid) + " " + collectPhotos.userId)
                    Text(text = stringResource(id = R.string.score) + " " + collectPhotos.score)
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }


        }
    }
}
