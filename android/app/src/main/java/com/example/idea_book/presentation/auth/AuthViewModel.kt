package com.example.idea_book.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea_book.domain.model.AuthActionResult
import com.example.idea_book.domain.use_cases.auth.GetUserUseCase
import com.example.idea_book.domain.use_cases.auth.SignInUseCase
import com.example.idea_book.domain.use_cases.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    private var _authState by mutableStateOf(AuthState())
    val authState: AuthState get() = _authState

    init {
        val user = getUserUseCase()
        if (user != null) {
            _authState = AuthState(
                isAuth = true
            )
        }
    }

    private fun signIn(email: String, password: String) {
        launchInViewModelScope { signInUseCase(email, password) }
    }

    private fun signUp(username: String, email: String, password: String) {
        launchInViewModelScope { signUpUseCase(username, email, password) }
    }

    private fun toggleAuthMode() {
        _authState = authState.copy(isLoginMode = !authState.isLoginMode)
        clearErrors()
    }

    private fun onChange(field: String, value: String) {
        when (field) {
            "email" -> _authState = authState.copy(email = value)
            "password" -> _authState = authState.copy(password = value)
            "username" -> _authState = authState.copy(username = value)
        }
    }

    private fun clearErrors() {
        _authState = authState.copy(
            emailError = null,
            passwordError = null,
            usernameError = null,
            firebaseError = null
        )
    }

    private fun launchInViewModelScope(authAction: suspend () -> AuthActionResult) {
        _authState = authState.copy(isLoading = true)
        viewModelScope.launch {
            val res = authAction()
            _authState = when (res) {
                is AuthActionResult.Success -> {
                    authState.copy(
                        isLoading = false,
                        isAuth = true
                    )
                }
                is AuthActionResult.Error -> {
                    val (emailError, passwordError, usernameError, firebaseError) = res
                    authState.copy(
                        emailError = emailError,
                        passwordError = passwordError,
                        usernameError = usernameError,
                        firebaseError = firebaseError,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.SignIn -> signIn(event.email, event.password)
            is AuthFormEvent.SignUp -> signUp(event.username, event.email, event.password)
            is AuthFormEvent.ToggleMode -> toggleAuthMode()
            is AuthFormEvent.EmailChange -> onChange("email", event.email)
            is AuthFormEvent.PasswordChange -> onChange("password", event.password)
            is AuthFormEvent.UsernameChange -> onChange("username", event.username)
            is AuthFormEvent.ClearErrors -> clearErrors()
        }
    }
}
