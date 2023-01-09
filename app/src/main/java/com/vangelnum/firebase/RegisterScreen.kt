package com.vangelnum.firebase

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(auth: FirebaseAuth, onNavigateToLogin: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        val emailValue = remember {
            mutableStateOf(TextFieldValue())
        }
        val passwordValue = remember {
            mutableStateOf(TextFieldValue())
        }
        val currentStateEyes = remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, start = 15.dp, end = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.joinnow),
                contentDescription = "join_now",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit,
            )
            Text(text = "Register",
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
            OutlinedTextField(value = emailValue.value, onValueChange = {
                emailValue.value = it
            }, modifier = Modifier.fillMaxWidth(), leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_alternate_email_24),
                    contentDescription = "email_icon")
            }, placeholder = {
                Text(text = "Email ID")
            }, maxLines = 1, singleLine = true)
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
                    VisualTransformation.None
            )
            OutlinedButton(onClick = {
                auth.createUserWithEmailAndPassword(
                    emailValue.value.text.trim(),
                    passwordValue.value.text.trim()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "Success")
                    } else {
                        Log.d("TAG", "Fail ${task.exception?.message.toString()}")
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(15.dp))
            {
                Text(text = "Register")
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Row(modifier = Modifier.clickable(onClick =
                    onNavigateToLogin
            ) ) {
                    Text(text = "Already have an account? ",
                        color = MaterialTheme.colors.onBackground)
                    Text(text = "Login", color = MaterialTheme.colors.primary)
                }
            }
        }
    }
}