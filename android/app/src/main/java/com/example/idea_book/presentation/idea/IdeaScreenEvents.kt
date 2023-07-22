package com.example.idea_book.presentation.idea

sealed class IdeaScreenEvents() {
    data class DeleteIdea(val ideaId: Int): IdeaScreenEvents()
}
