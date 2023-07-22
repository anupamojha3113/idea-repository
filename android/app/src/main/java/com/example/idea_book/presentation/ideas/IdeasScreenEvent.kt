package com.example.idea_book.presentation.ideas

import com.example.idea_book.domain.model.TagModel

sealed class IdeasScreenEvent {
    data class SearchChanged(val query: String) : IdeasScreenEvent()
    data class TagSelected(val tag: TagModel) : IdeasScreenEvent()
    object SearchIdeas: IdeasScreenEvent()
}
