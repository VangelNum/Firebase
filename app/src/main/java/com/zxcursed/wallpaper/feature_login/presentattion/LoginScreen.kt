package com.zxcursed.wallpaper.feature_login.presentattion

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.core.common.Resource
import com.zxcursed.wallpaper.core.data.Person
import com.zxcursed.wallpaper.core.presentation.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldState: ScaffoldState
) {

    val auth = Firebase.auth
    val state = viewModel.loginFlow.collectAsState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = account.getResult(ApiException::class.java)
                    val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                    auth.signInWithCredential(credentials).await()
                    withContext(Dispatchers.Main) {
                        return@withContext navController.navigate(Screens.Main.route) {
                            popUpTo(Screens.Login.route) {
                                inclusive = true
                            }
                            popUpTo(Screens.Register.route) {
                                inclusive = true
                            }
                        }
                    }
                } catch (it: ApiException) {
                    Log.d("Error", it.status.toString())
                }
            }
        }

    val emailValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val passwordValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val currentStateEyes = remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 15.dp, end = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 15.dp,
                alignment = Alignment.CenterVertically
            ),
        ) {
            Text(
                text = stringResource(id = R.string.lets_sign_you),
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextField(
                value = emailValue.value,
                onValueChange = {
                    emailValue.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_alternate_email_24),
                        contentDescription = "email_icon"
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.email_id))
                },
                maxLines = 1,
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            OutlinedTextField(
                value = passwordValue.value,
                onValueChange = {
                    passwordValue.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                        contentDescription = "email_icon"
                    )
                },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                trailingIcon = {
                    IconButton(onClick = { currentStateEyes.value = !currentStateEyes.value }) {
                        Icon(
                            painter = if (!currentStateEyes.value) painterResource(id = R.drawable.closeeyes) else painterResource(
                                id = R.drawable.openeyes
                            ), contentDescription = "eyes"
                        )
                    }
                },
                maxLines = 1,
                singleLine = true,
                visualTransformation = if (!currentStateEyes.value) PasswordVisualTransformation() else
                    VisualTransformation.None,
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            OutlinedButton(
                onClick = {
                    if (emailValue.value.text.trim() != "" && passwordValue.value.text.trim() != "") {
                        viewModel.loginUser(
                            emailValue.value.text.trim(),
                            passwordValue.value.text.trim()
                        )
                        keyboardController?.hide()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.empty_field),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(15.dp)
            )
            {
                Text(text = stringResource(id = R.string.sign_in))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(modifier = Modifier.weight(2f))
                Text(
                    text = stringResource(id = R.string.or),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Divider(modifier = Modifier.weight(2f))
            }

            OutlinedButton(
                onClick = {
                    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    val mGoogleSignInClient = GoogleSignIn.getClient(context, options)

                    val signInIntent = mGoogleSignInClient.signInIntent
                    launcher.launch(signInIntent)

                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), shape = RoundedCornerShape(15.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_svg),
                        contentDescription = "google_svg", tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(id = R.string.continue_with_google))
                }
            }



            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Row(
                    modifier = Modifier.clickable(
                        onClick = { navController.navigate(Screens.Register.route) }
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_have_account) + " ",
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }

        state.value?.let {
            when (it) {
                is Resource.Error -> {
                    LaunchedEffect(key1 = Unit) {
                        Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Success -> {
                    LaunchedEffect(key1 = Unit) {
                        val person = Person(emailValue.value.text, passwordValue.value.text)
                        viewModel.saveData(person)
                        if (auth.currentUser?.isEmailVerified == true) {
                            navController.navigate(Screens.Main.route) {
                                popUpTo(Screens.Login.route) {
                                    inclusive = true
                                }
                                popUpTo(Screens.Register.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            val result =
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "Необходимо подтвердить Email, отправить ссылку ещё раз?",
                                    "Yep"
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    auth.currentUser?.sendEmailVerification()
                                }
                                else -> Unit
                            }
                        }
                    }
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

    }
}
