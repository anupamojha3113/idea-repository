package com.example.idea_book.domain.use_cases.ideas

import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val ideasRepository: IdeasRepository
) {
    suspend operator fun invoke(token: String): List<TagModel> {
        return ideasRepository.getTags(token)
    }
}