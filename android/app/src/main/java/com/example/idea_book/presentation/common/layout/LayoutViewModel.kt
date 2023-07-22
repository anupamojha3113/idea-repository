package com.example.idea_book.presentation.common.layout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea_book.domain.use_cases.auth.GetTokenUseCase
import com.example.idea_book.domain.use_cases.auth.GetUserUseCase
import com.example.idea_book.domain.use_cases.auth.SignOutUseCase
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LayoutViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase,
): ViewModel() {
    var user by mutableStateOf(getUserUseCase()!!)
    var token: String? by mutableStateOf(null)

    fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch {
            signOutUseCase()
            onComplete()
        }
    }
}
