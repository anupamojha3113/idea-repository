package com.example.idea_book.domain.use_cases.auth

import com.example.idea_book.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.signOut()
    }
}
