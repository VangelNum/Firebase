package com.vangelnum.firebase

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(onRegisterScreen: () -> Unit, auth: FirebaseAuth) {
    val emailValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val passwordValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val currentStateEyes = remember {
        mutableStateOf(false)
    }
    val errorRegisterText = remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp, start = 15.dp, end = 15.dp)
            .clickable {
                keyboardController?.hide()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 15.dp,
            alignment = Alignment.CenterVertically),
    ) {
        Text(text = "Let's sign you in",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold))
        Text(text = "Welcome back.", style = MaterialTheme.typography.h4)
        Text(text = "You've been missed!", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(value = emailValue.value,
            onValueChange = {
                emailValue.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_alternate_email_24),
                    contentDescription = "email_icon")
            },
            placeholder = {
                Text(text = "Email ID")
            },
            maxLines = 1,
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }))
        OutlinedTextField(value = passwordValue.value,
            onValueChange = {
                passwordValue.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_lock_24),
                    contentDescription = "email_icon")
            },
            placeholder = {
                Text(text = "Password")
            },
            trailingIcon = {
                IconButton(onClick = { currentStateEyes.value = !currentStateEyes.value }) {
                    Icon(painter = if (!currentStateEyes.value) painterResource(id = R.drawable.closeeyes) else painterResource(
                        id = R.drawable.openeyes), contentDescription = "eyes")
                }
            },
            maxLines = 1,
            singleLine = true,
            visualTransformation = if (!currentStateEyes.value) PasswordVisualTransformation() else
                VisualTransformation.None,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        val context = LocalContext.current
        OutlinedButton(onClick = {
            if (emailValue.value.text != "" || passwordValue.value.text != "") {
                auth.signInWithEmailAndPassword(
                    emailValue.value.text.trim(),
                    passwordValue.value.text.trim()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    } else {
                        errorRegisterText.value = task.exception?.message.toString()
                    }
                }
            } else {
                errorRegisterText.value = "Empty Field"
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            shape = RoundedCornerShape(15.dp))
        {
            Text(text = "Sign in")
        }

        Text(text = errorRegisterText.value, color = Color.Red)

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Row(modifier = Modifier.clickable(onClick = onRegisterScreen
            )) {
                Text(text = "Don't have an account? ",
                    color = MaterialTheme.colors.onBackground)
                Text(text = "Register", color = MaterialTheme.colors.primary)
            }
        }
    }

}