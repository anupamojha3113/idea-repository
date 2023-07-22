package com.example.idea_book.data

import com.example.idea_book.data.dto.request.IdeaReq
import com.example.idea_book.data.dto.response.IdeaRes
import com.example.idea_book.domain.model.TagModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface IdeaBookApi {
    @GET("ideas")
    suspend fun getIdeas(@Header("Authorization") token: String, @Query("tags") tags: String? = "", @Query("search") search: String? = ""): List<IdeaRes>

    @POST("ideas")
    suspend fun createIdea(@Body idea: IdeaReq, @Header("Authorization") token: String): String

    @DELETE("ideas/{id}")
    suspend fun deleteIdea(@Path("id") id: Int, @Header("Authorization") token: String): String

    @GET("tags")
    suspend fun getTags(@Header("Authorization") token: String): List<TagModel>

    @POST("ideas/{id}/like")
    suspend fun likeIdea(@Path("id") id: Int, @Header("Authorization") token: String): String

    @DELETE("ideas/{id}/like")
    suspend fun unlikeIdea(@Path("id") id: Int, @Header("Authorization") token: String): String

    @GET("ideas/me")
    suspend fun getMyIdeas(@Header("Authorization") token: String, @Query("tags") tags: String? = "", @Query("search") search: String? = ""): List<IdeaRes>

    @GET("ideas/{id}")
    suspend fun getIdea(@Path("id") id: Int, @Header("Authorization") token: String): IdeaRes

    @PUT("ideas/{id}")
    suspend fun updateIdea(@Path("id") id: Int, @Body idea: IdeaReq, @Header("Authorization") token: String): String
}
