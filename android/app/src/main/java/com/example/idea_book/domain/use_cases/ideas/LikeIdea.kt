package com.example.idea_book.domain.use_cases.ideas

import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class LikeIdeaUseCase @Inject constructor(
    private val ideasRepository: IdeasRepository
) {
    suspend operator fun invoke(id: Int, token: String) {
        ideasRepository.likeIdea(id, token)
    }
}