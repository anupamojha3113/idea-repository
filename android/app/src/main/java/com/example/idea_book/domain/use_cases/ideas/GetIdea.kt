package com.example.idea_book.domain.use_cases.ideas

import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class GetIdeaUseCase @Inject constructor(
    private val ideasRepository: IdeasRepository
) {
    suspend operator fun invoke(ideaId: Int, token: String) = ideasRepository.getIdea(ideaId, token)
}
