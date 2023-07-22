package com.example.idea_book.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class IdeaModel(
    val id: Int,
    val title: String,
    val description: String,
    val username: String,
    val user_id: String,
    val likes_total: Int,
    val comments_total: Int,
    val tags: List<TagModel>,
    val likes: List<String>,
    val created_at: String,
)

data class TagModel(
    val id: Int,
    val name: String,
    val icon: String
)
