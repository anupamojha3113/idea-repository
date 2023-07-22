package com.example.idea_book.domain.repository

import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.model.TagModel

interface IdeasRepository {
    suspend fun getIdeas(token: String, tags: String = "", searchQuery: String = ""): List<IdeaModel>
    suspend fun deleteIdea(id: Int, token: String): Boolean
    suspend fun createIdea(
        title: String,
        description: String,
        tags: List<TagModel>,
        token: String
    ): Boolean
    suspend fun getTags(token: String): List<TagModel>
    suspend fun likeIdea(id: Int, token: String): Boolean
    suspend fun unlikeIdea(id: Int, token: String): Boolean
    suspend fun getMyIdeas(token: String, tags: String = "", searchQuery: String = ""): List<IdeaModel>
    suspend fun getIdea(id: Int, token: String): IdeaModel
    suspend fun updateIdea(
        id: Int,
        title: String,
        description: String,
        tags: List<TagModel>,
        token: String
    ): Boolean
}