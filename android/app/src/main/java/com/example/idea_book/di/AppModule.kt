package com.example.idea_book.di

import com.example.idea_book.core.constants.Constants
import com.example.idea_book.data.IdeaBookApi
import com.example.idea_book.data.repository.AuthRepositoryImpl
import com.example.idea_book.data.repository.IdeasRepositoryImpl
import com.example.idea_book.domain.repository.AuthRepository
import com.example.idea_book.domain.repository.IdeasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideIdeaBookApi(): IdeaBookApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IdeaBookApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIdeasRepository(api: IdeaBookApi): IdeasRepository {
        return IdeasRepositoryImpl(api)
    }
}
