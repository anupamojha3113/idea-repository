package com.example.idea_book.domain.model

import androidx.compose.runtime.Composable

data class MenuItem(
    val title: String,
    val icon: @Composable () -> Unit,
)
