package com.example.idea_book.core.validators

fun usernameValidator(username: String): String? {
    return when {
        username.isEmpty() -> "Username cannot be empty"
        username.length < 3 -> "Username must be at least 3 characters long"
        username.length > 20 -> "Username must be at most 20 characters long"
        else -> null
    }
}
