package com.example.idea_book.presentation.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.idea_book.R
import com.example.idea_book.presentation.auth.AuthFormEvent
import com.example.idea_book.presentation.auth.AuthState

@Composable
fun AuthForm(state: AuthState, emitEvent: (AuthFormEvent) -> Unit) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        HeaderText()
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 8.dp,
            modifier = Modifier.widthIn(max = 350.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (state.firebaseError != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        backgroundColor = MaterialTheme.colors.error
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(16.dp),
                                overflow = TextOverflow.Ellipsis,
                                text = state.firebaseError
                            )
                            IconButton(onClick = { emitEvent(AuthFormEvent.ClearErrors) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "Close",
                                    tint = MaterialTheme.colors.onError,
                                )
                            }
                        }
                    }
                }
                if (!state.isLoginMode)
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { emitEvent(AuthFormEvent.UsernameChange(it)) },
                        label = { Text(text = "Username") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        isError = state.usernameError != null,
                    )
                if (state.usernameError != null)
                    Text(
                        text = state.usernameError,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Start)
                    )
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { emitEvent(AuthFormEvent.EmailChange(it)) },
                    label = @Composable { Text("Email") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                    isError = state.emailError != null,
                )
                if (state.emailError != null)
                    Text(
                        text = state.emailError,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Start)
                    )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { emitEvent(AuthFormEvent.PasswordChange(it)) },
                    label = @Composable { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    isError = state.passwordError != null,
                )
                if (state.passwordError != null)
                    Text(
                        text = state.passwordError,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Start)
                    )
                Button(
                    onClick = {
                        if (state.isLoginMode)
                            emitEvent(AuthFormEvent.SignIn(state.email, state.password))
                        else
                            emitEvent(
                                AuthFormEvent.SignUp(
                                    state.username,
                                    state.email,
                                    state.password
                                )
                            )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    enabled = state.isLoading.not()
                ) {
                    Text(text = if (state.isLoginMode) "Login" else "Signup")
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (state.isLoginMode)
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { }
                    )
                Row {
                    Text(
                        text = if (state.isLoginMode) "Don't have an account?" else "Already has an account ?",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (state.isLoginMode) "Signup" else "Login",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { emitEvent(AuthFormEvent.ToggleMode) }
                    )
                }
            }
        }
    }
}
