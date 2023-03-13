package com.zxcursed.wallpaper.feature_add_photo.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.SubcomposeAsyncImage
import com.zxcursed.wallpaper.R

@Composable
fun AddPhotoFromLink() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LimitationHeader()
        LinkBody()
    }
}

@Composable
fun LinkBody(viewModel: AddPhotoViewModel = hiltViewModel()) {
    val textValue = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    OutlinedTextField(value = textValue.value, onValueChange = {
        textValue.value = it
    }, modifier = Modifier
        .fillMaxWidth()
        .height(68.dp),
        label = { Text(text = stringResource(id = R.string.url_to_zxcursed)) },
        maxLines = 1, shape = RoundedCornerShape(25.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { textValue.value = "" }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_close_24),
                    contentDescription = "delete field"
                )
            }
        })

    OutlinedButton(
        onClick = {
            if (textValue.value.isNotBlank()) {
                viewModel.addPhoto(textValue.value, context = context)
            } else {
                Toast.makeText(context, context.getString(R.string.cant_be_empty), Toast.LENGTH_SHORT)
                    .show()
            }
        }, colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier.height(60.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(
            text = stringResource(id = R.string.send_url),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
            )
        )
    }
    if (textValue.value.isEmpty()) {
        Text(
            text = stringResource(id = R.string.error_for_photo_zxcursed),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(bottom = 10.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            SubcomposeAsyncImage(
                model = textValue.value,
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                },
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Crop
            )

        }
    }
}