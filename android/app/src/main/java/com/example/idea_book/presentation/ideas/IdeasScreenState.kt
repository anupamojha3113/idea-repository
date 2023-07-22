package com.example.idea_book.presentation.ideas

import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.model.TagModel

data class IdeasScreenState(
    val isLoading: Boolean = true,
    val ideas: List<IdeaModel> = emptyList(),
    val tags: List<TagModel> = emptyList(),
    val selectedTags: List<TagModel> = emptyList(),
    val search: String = "",
    val error: String = ""
)
