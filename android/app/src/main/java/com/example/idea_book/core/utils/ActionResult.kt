package com.example.idea_book.core.utils

sealed class ActionResult() {
    object Success: ActionResult()
    data class Error(val message: String): ActionResult()
}
