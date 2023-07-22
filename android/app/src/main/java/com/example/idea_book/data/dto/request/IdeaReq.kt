package com.example.idea_book.data.dto.request

data class IdeaReq(
    val title: String,
    val description: String,
    val tags: List<String>
)
