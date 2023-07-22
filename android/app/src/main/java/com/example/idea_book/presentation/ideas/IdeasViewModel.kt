package com.example.idea_book.presentation.ideas

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idea_book.domain.use_cases.auth.GetTokenUseCase
import com.example.idea_book.domain.use_cases.auth.GetUserUseCase
import com.example.idea_book.domain.use_cases.ideas.GetIdeasUseCase
import com.example.idea_book.domain.use_cases.ideas.GetTagsUseCase
import com.example.idea_book.domain.use_cases.ideas.LikeIdeaUseCase
import com.example.idea_book.domain.use_cases.ideas.UnLikeIdeaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdeasViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getIdeasUseCase: GetIdeasUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val likeIdeaUseCase: LikeIdeaUseCase,
    private val unLikeIdeaUseCase: UnLikeIdeaUseCase,
    getUserUseCase: GetUserUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _user by mutableStateOf(getUserUseCase())
    val userId get() = _user?.uid

    private var _state by mutableStateOf(IdeasScreenState())

    val state: IdeasScreenState
        get() = _state

    private val _events = MutableSharedFlow<UIEvent>()
    val events = _events.asSharedFlow()

    private var getIdeasJob: Job? = null

    init {
        viewModelScope.launch {
            val token = getTokenUseCase()
            if (token != null) {
                getIdeas(token)
                val tags = getTagsUseCase(token)
                _state = _state.copy(tags = tags)
            }
        }
    }

    fun onEvent(event: IdeasScreenEvent) {
        when (event) {
            is IdeasScreenEvent.SearchChanged -> {
                _state = _state.copy(search = event.query)
            }
            is IdeasScreenEvent.SearchIdeas -> {
                _state = _state.copy(isLoading = true)
                getIdeas()
            }
            is IdeasScreenEvent.TagSelected -> {
                _state = if (state.selectedTags.contains(event.tag)) {
                    state.copy(
                        selectedTags = state.selectedTags.filter { it != event.tag },
                        isLoading = true
                    )
                } else {
                    state.copy(
                        selectedTags = state.selectedTags + event.tag,
                        isLoading = true
                    )
                }
                getIdeas()
            }
        }
    }

    fun likeIdea(ideaId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            val token = getTokenUseCase()
            if (token != null) {
                try {
                    if (isLiked) {
                        likeIdeaUseCase(ideaId, token)
                    } else {
                        unLikeIdeaUseCase(ideaId, token)
                    }
                } catch (_: Exception) {}
            }
        }
    }

    private fun getIdeas(token: String? = null) {
        getIdeasJob?.cancel()
        getIdeasJob = viewModelScope.launch {
            val safeToken = token ?: getTokenUseCase()
            val ideas = getIdeasUseCase(
                safeToken!!,
                state.selectedTags,
                state.search,
                myIdeas = savedStateHandle.get<Boolean>("myIdeas") ?: false
            )
            _state = _state.copy(ideas = ideas, isLoading = false)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val msg: String) : UIEvent()
    }
}
