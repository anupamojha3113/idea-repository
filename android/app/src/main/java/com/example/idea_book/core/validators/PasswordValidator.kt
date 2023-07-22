package com.example.idea_book.core.validators

fun passwordValidator(password: String): String? {
    return when {
        password.isEmpty() -> "Password cannot be empty"
        password.length < 8 -> "Password must be at least 8 characters"
        !password.contains(Regex("[A-Z]")) -> "Password must contain at least one uppercase letter"
        !password.contains(Regex("[a-z]")) -> "Password must contain at least one lowercase letter"
        !password.contains(Regex("[0-9]")) -> "Password must contain at least one number"
        else -> null
    }
}
