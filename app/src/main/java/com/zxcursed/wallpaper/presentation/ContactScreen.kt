package com.zxcursed.wallpaper.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zxcursed.wallpaper.R

@Composable
fun ContactScreen() {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item() {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = stringResource(id = R.string.check),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.check3),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            val mailto = "mailto:vangelnum@gmail.com" +
                                    "?cc=" +
                                    "&subject=" + Uri.encode("Zxcursed Wallpaper")
                            val emailIntent = Intent(Intent.ACTION_SENDTO)
                            emailIntent.data = Uri.parse(mailto)
                            context.startActivity(emailIntent)
                        }
                )
                Text(
                    text = stringResource(id = R.string.check2),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            val uri: Uri = Uri.parse("https://vk.com/vangelnum")
                            val browser = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(browser)
                        }
                )
            }
            item() {
                Text(
                    text = stringResource(id = R.string.check5),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.check6),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            val uri: Uri = Uri.parse("https://www.youtube.com/zxcursed")
                            val browser = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(browser)
                        }
                )
                Text(
                    text = stringResource(id = R.string.check7),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            val uri: Uri = Uri.parse("https://t.me/zxcursed")
                            val browser = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(browser)
                        }
                )
                Text(
                    text = stringResource(id = R.string.check8),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.atypdisplaynew)),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable {
                            val uri: Uri = Uri.parse("https://vk.com/zxcursedd")
                            val browser = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(browser)
                        }
                )
            }
        }
    }

}