package com.example.idea_book.presentation.auth

data class AuthState(
    val isLoginMode: Boolean = true,
    val isAuth: Boolean = false,
    val isLoading: Boolean = false,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val firebaseError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)
