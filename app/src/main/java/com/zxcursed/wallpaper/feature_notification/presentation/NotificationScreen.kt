package com.zxcursed.wallpaper.feature_notification.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.core.presentation.navigation.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val resources = viewModel.allNotifications.value

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = resources.isLoading)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.getAllNotifications() },
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                contentColor = Color.Green,
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
            ) {
                resources.data.notification?.let {
                    itemsIndexed(it) { _, res ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(120.dp),
                                shape = RoundedCornerShape(25.dp)
                            ) {
                                SubcomposeAsyncImage(
                                    model = res.name1,
                                    contentDescription = "photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop,
                                    loading = {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    },
                                    error = {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_baseline_error_24),
                                            contentDescription = "error"
                                        )
                                    }
                                )
                            }
                            SelectionContainer {
                                Text(
                                    text = res.name1!!,
                                    maxLines = 5,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data = Uri.parse(res.name1)
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        e.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }, colors = ButtonDefaults.buttonColors(Color.Transparent),
                            modifier = Modifier.height(60.dp),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.open_photo),
                                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light,
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = {
                                val encodedUrl =
                                    URLEncoder.encode(res.name1, StandardCharsets.UTF_8.toString())
                                navController.navigate(Screens.WatchPhoto.withArgs(encodedUrl))
                            }, colors = ButtonDefaults.buttonColors(Color.Transparent),
                            modifier = Modifier.height(60.dp),
                            shape = RoundedCornerShape(25.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.watch_photo),
                                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light,
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        if (res.name2 == stringResource(id = R.string.green)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.status_on_apply),
                                color = Color.Green,
                                fontFamily = FontFamily(Font(R.font.atypdisplaynew))
                            )
                        }
                        if (res.name2 == stringResource(id = R.string.red)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.status_on_rejected),
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.atypdisplaynew))
                            )
                        }
                        if (res.name2 == stringResource(id = R.string.gray)) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.status_on_review),
                                color = Color.Gray,
                                fontFamily = FontFamily(Font(R.font.atypdisplaynew))
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(thickness = 1.dp, color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }
        if (resources.data.notification?.isEmpty() == true) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.no_notification))
                    OutlinedButton(
                        onClick = { viewModel.getAllNotifications() },
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
        if (resources.error.isNotBlank()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = resources.error)
                    OutlinedButton(
                        onClick = { viewModel.getAllNotifications() },
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
    }


}
