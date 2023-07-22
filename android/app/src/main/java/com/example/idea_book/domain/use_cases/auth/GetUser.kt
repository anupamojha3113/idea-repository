package com.example.idea_book.domain.use_cases.auth

import com.example.idea_book.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.getUser()
}
