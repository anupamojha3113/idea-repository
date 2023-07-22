package com.example.idea_book.domain.model

sealed class AuthActionResult() {
    class Success() : AuthActionResult()
    data class Error(
        val emailErr: String? = null,
        val passwordErr: String? = null,
        val usernameErr: String? = null,
        val firebaseErr: String? = null,
    ) : AuthActionResult()
}
