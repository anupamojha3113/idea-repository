package com.example.idea_book.presentation.create_idea

import androidx.compose.ui.focus.FocusState
import com.example.idea_book.domain.model.TagModel

sealed class CreateIdeaEvent{
    data class EnteredTitle(val value: String): CreateIdeaEvent()
    data class ChangeTitleFocus(val focusState: FocusState): CreateIdeaEvent()
    data class EnteredContent(val value: String): CreateIdeaEvent()
    data class ChangeContentFocus(val focusState: FocusState): CreateIdeaEvent()
    data class TagSelected(val value: TagModel): CreateIdeaEvent()
    object SaveIdea: CreateIdeaEvent()
}
