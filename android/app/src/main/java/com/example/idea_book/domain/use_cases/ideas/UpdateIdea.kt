package com.example.idea_book.domain.use_cases.ideas

import com.example.idea_book.core.utils.ActionResult
import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class UpdateIdeaUseCase @Inject constructor(
    private val ideasRepository: IdeasRepository
) {
    suspend operator fun invoke(
        id: Int,
        title: String,
        description: String,
        tags: List<TagModel>,
        token: String
    ): ActionResult {
        if (title.isEmpty()) {
            return ActionResult.Error("Title cannot be empty")
        } else if (description.isEmpty()) {
            return ActionResult.Error("Content cannot be empty")
        } else if (tags.isEmpty()) {
            return ActionResult.Error("You must select at least one tag")
        }

        ideasRepository.updateIdea(
            id = id,
            title = title,
            description = description,
            tags = tags,
            token = token
        )

        return ActionResult.Success
    }
}