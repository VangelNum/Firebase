package com.zxcursed.wallpaper.feature_add_photo.presentation

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.zxcursed.wallpaper.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddPhotoScreen(viewModel: AddPhotoViewModel = hiltViewModel()) {

    val textValue = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.your_variants_for_photo),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.restrictions),
            color = Color.Red,
            style = MaterialTheme.typography.body2
        )
        Text(text = stringResource(id = R.string.restriction1))
        Text(text = stringResource(id = R.string.restriction2))

        OutlinedTextField(value = textValue.value, onValueChange = {
            textValue.value = it
        }, modifier = Modifier.fillMaxWidth(), label = {
            Text(text = stringResource(id = R.string.url_to_zxcursed))
        }, maxLines = 1, shape = RoundedCornerShape(15.dp), singleLine = true, trailingIcon = {
            IconButton(onClick = { textValue.value = "" }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_close_24),
                    contentDescription = "delete field"
                )
            }
        })
        OutlinedButton(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addPhoto(textValue.value)
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = stringResource(id = R.string.send))
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
                    .height(400.dp)
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
}