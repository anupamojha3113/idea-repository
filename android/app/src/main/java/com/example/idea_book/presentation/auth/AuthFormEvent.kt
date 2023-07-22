package com.example.idea_book.presentation.auth

sealed class AuthFormEvent {
    data class SignIn(val email: String, val password: String) : AuthFormEvent()
    data class SignUp(val username: String, val email: String, val password: String) : AuthFormEvent()
    data class EmailChange(val email: String) : AuthFormEvent()
    data class PasswordChange(val password: String) : AuthFormEvent()
    data class UsernameChange(val username: String) : AuthFormEvent()
    object ToggleMode : AuthFormEvent()
    object ClearErrors: AuthFormEvent()
}
