package com.example.idea_book.domain.use_cases.ideas

import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class DeleteIdeaUseCase @Inject constructor(
    private val ideasRepository: IdeasRepository
) {
    suspend operator fun invoke(ideaId: Int, token: String) {
        ideasRepository.deleteIdea(ideaId, token)
    }
}
