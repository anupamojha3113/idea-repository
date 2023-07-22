package com.example.idea_book.presentation.idea

import com.example.idea_book.domain.model.IdeaModel

data class IdeaState(
    val isLoading: Boolean,
    var idea: IdeaModel? = null,
)
