package com.example.idea_book.presentation.idea

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.idea_book.presentation.common.BlankScreen
import com.example.idea_book.presentation.common.layout.Layout
import com.example.idea_book.presentation.destinations.CreateIdeaScreenDestination
import com.example.idea_book.presentation.destinations.IdeasScreenDestination
import com.example.idea_book.presentation.idea.components.DisplayIdea
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun IdeaScreen(
    navigator: DestinationsNavigator, viewModel: IdeaViewModel = hiltViewModel(), ideaId: Int
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is IdeaViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg
                    )
                    if (event.navigate) {
                        navigator.navigate(IdeasScreenDestination())
                    }
                }
            }
        }
    }

    if (viewModel.isLoading) {
        Layout(navigator = navigator, scaffoldState = scaffoldState) {
            BlankScreen(showLoadingSpinner = true, modifier = Modifier.fillMaxSize())
        }
        return
    }

    val idea = viewModel.idea!!
    Layout(navigator = navigator, scaffoldState = scaffoldState) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize(),
            contentColor = MaterialTheme.colors.onBackground,
        ) {
            DisplayIdea(idea = idea, userId = viewModel.user?.uid!!, onDelete = {
                viewModel.onEvent(IdeaScreenEvents.DeleteIdea(ideaId))
            }, onEdit = {
                navigator.navigate(CreateIdeaScreenDestination(
                    ideaId = ideaId.toString(),
                ))
            })
        }
    }
}
