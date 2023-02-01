package com.zxcursed.wallpaper.feature_notification.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import coil.compose.SubcomposeAsyncImage
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.Screens
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val resourses = viewModel.allNotifications.value

    if (resourses.data.notification?.isEmpty() == true) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(id = R.string.no_notification))
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(25.dp),
    ) {
        resourses.data.notification?.let {
            itemsIndexed(it) { index, res ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .size(120.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = res.name1,
                            contentDescription = "photo",
                            modifier = Modifier.fillMaxSize(),
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
                    SelectionContainer() {
                        Text(text = res.name1!!)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(res.name1)
                    context.startActivity(intent)
                }) {
                    Text(
                        text = stringResource(id = R.string.open_photo),
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(onClick = {
                    val encodedUrl = URLEncoder.encode(res.name1, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screens.WatchPhoto.withArgs(encodedUrl))
                }) {
                    Text(
                        text = stringResource(id = R.string.watch_photo),
                        modifier = Modifier.fillMaxWidth(),
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
                        color = Color.Green
                    )
                }
                if (res.name2 == stringResource(id = R.string.red)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.status_on_rejected),
                        color = Color.Red
                    )
                }
                if (res.name2 == stringResource(id = R.string.gray)) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.status_on_review),
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Divider(thickness = 1.dp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }


}
