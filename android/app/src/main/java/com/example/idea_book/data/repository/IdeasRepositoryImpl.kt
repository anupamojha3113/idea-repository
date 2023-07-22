package com.example.idea_book.data.repository

import android.util.Log
import com.example.idea_book.data.IdeaBookApi
import com.example.idea_book.data.dto.request.IdeaReq
import com.example.idea_book.data.dto.response.toModel
import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.domain.repository.IdeasRepository
import javax.inject.Inject

class IdeasRepositoryImpl @Inject constructor(
    private val ideaBookApi: IdeaBookApi
) : IdeasRepository {
    override suspend fun getIdeas(token: String, tags: String, searchQuery: String): List<IdeaModel> {
        return ideaBookApi.getIdeas(token, tags, searchQuery).map { it.toModel() }
    }

    override suspend fun createIdea(
        title: String,
        description: String,
        tags: List<TagModel>,
        token: String
    ): Boolean {
        val ideaReq = IdeaReq(
            title = title,
            description = description,
            tags = tags.map { it.name }
        )
        ideaBookApi.createIdea(ideaReq, token)
        return true
    }

    override suspend fun deleteIdea(id: Int, token: String): Boolean {
        ideaBookApi.deleteIdea(id, token)
        return true
    }

    override suspend fun getTags(token: String): List<TagModel> {
        return ideaBookApi.getTags(token)
    }

    override suspend fun getMyIdeas(
        token: String,
        tags: String,
        searchQuery: String
    ): List<IdeaModel> {
        return ideaBookApi.getMyIdeas(token, tags, searchQuery).map { it.toModel() }
    }

    override suspend fun likeIdea(id: Int, token: String): Boolean {
        ideaBookApi.likeIdea(id, token)
        return true
    }

    override suspend fun unlikeIdea(id: Int, token: String): Boolean {
        ideaBookApi.unlikeIdea(id, token)
        return true
    }

    override suspend fun getIdea(id: Int, token: String): IdeaModel {
        return ideaBookApi.getIdea(id, token).toModel()
    }

    override suspend fun updateIdea(
        id: Int,
        title: String,
        description: String,
        tags: List<TagModel>,
        token: String
    ): Boolean {
        val ideaReq = IdeaReq(
            title = title,
            description = description,
            tags = tags.map { it.name }
        )
        ideaBookApi.updateIdea(id, ideaReq, token)
        return true
    }
}
