package com.example.idea_book.domain.use_cases.auth

import com.example.idea_book.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? {
        val res = authRepository.getUser()?.getIdToken(true)?.await()
        return res?.token
    }
}