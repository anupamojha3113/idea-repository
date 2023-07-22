package com.example.idea_book.domain.use_cases.ideas

import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class GetIdeasUseCase @Inject constructor(
    private val ideasRepository: IdeasRepository
) {
    suspend operator fun invoke(
        token: String,
        tags: List<TagModel>,
        searchQuery: String,
        myIdeas: Boolean = false
    ): List<IdeaModel> {
        val tagsString = tags.joinToString(",") { it.name }

        if (myIdeas) {
            return ideasRepository.getMyIdeas(token, tagsString, searchQuery)
        }

        return ideasRepository.getIdeas(token, tagsString, searchQuery)
    }
}
