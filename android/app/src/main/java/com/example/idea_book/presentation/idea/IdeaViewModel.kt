package com.example.idea_book.presentation.idea

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.use_cases.auth.GetTokenUseCase
import com.example.idea_book.domain.use_cases.auth.GetUserUseCase
import com.example.idea_book.domain.use_cases.ideas.DeleteIdeaUseCase
import com.example.idea_book.domain.use_cases.ideas.GetIdeaUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdeaViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getIdeaUseCase: GetIdeaUseCase,
    private val deleteIdeaUseCase: DeleteIdeaUseCase,
    getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _user by mutableStateOf<FirebaseUser?>(getUserUseCase())
    private var _state by mutableStateOf(IdeaState(isLoading = true, idea = null))
    val isLoading: Boolean
        get() = _state.isLoading
    val idea: IdeaModel?
        get() = _state.idea

    val user: FirebaseUser?
        get() = _user

    private val _events = MutableSharedFlow<UIEvent>()
    val events = _events.asSharedFlow()

    init {
        savedStateHandle.get<Int>("ideaId")?.let { ideaId ->
            viewModelScope.launch {
                val token = getTokenUseCase()
                if (token != null) {
                    val idea = getIdeaUseCase(ideaId, token)
                    _state = IdeaState(isLoading = false, idea = idea)
                }
            }
        }
    }

    fun onEvent(event: IdeaScreenEvents) {
        when (event) {
            is IdeaScreenEvents.DeleteIdea -> {
                viewModelScope.launch {
                    getTokenUseCase()?.let { token ->
                        deleteIdeaUseCase(event.ideaId, token)
                        _events.emit(
                            UIEvent.ShowSnackBar(
                                "Idea deleted you will be redirected back in few seconds",
                                true
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val msg: String, val navigate: Boolean = false) : UIEvent()
    }
}
