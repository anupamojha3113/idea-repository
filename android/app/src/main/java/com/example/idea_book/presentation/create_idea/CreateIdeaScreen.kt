package com.example.idea_book.presentation.create_idea

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.idea_book.core.constants.Save
import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.presentation.common.TagList
import com.example.idea_book.presentation.common.layout.Layout
import com.example.idea_book.presentation.create_idea.components.TransparentHintTextField
import com.example.idea_book.presentation.destinations.IdeaScreenDestination
import com.example.idea_book.presentation.destinations.IdeasScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun CreateIdeaScreen(
    navigator: DestinationsNavigator,
    viewModel: CreateIdeaViewModel = hiltViewModel(),
    ideaId: String? = null,
) {
    val scaffoldState = rememberScaffoldState()
    val title = viewModel.ideaTitle.value
    val content = viewModel.ideaContent.value

    LaunchedEffect(key1 = true) {
        viewModel.events.collectLatest {
            when (it) {
                is CreateIdeaViewModel.UIEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                    when(it.navigate) {
                        "IdeaScreen" -> navigator.navigate(IdeaScreenDestination(ideaId?.toInt()!!))
                        "IdeasScreen" -> navigator.navigate(IdeasScreenDestination(true))
                    }
                }
            }
        }
    }

    Layout(navigator, scaffoldState = scaffoldState, floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(CreateIdeaEvent.SaveIdea) }) {
            Icon(Save, contentDescription = "Save")
        }
    }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = title.text,
                    hint = title.hint,
                    onValueChange = {
                        viewModel.onEvent(CreateIdeaEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(CreateIdeaEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = title.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = content.text,
                    hint = content.hint,
                    onValueChange = {
                        viewModel.onEvent(CreateIdeaEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(CreateIdeaEvent.ChangeContentFocus(it))
                    },
                    isHintVisible = content.isHintVisible,
                    textStyle = MaterialTheme.typography.body1,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TagList(
                    tags = viewModel.tags,
                    selectedTags = viewModel.selectedTags,
                    onTagSelected = {
                        viewModel.onEvent(CreateIdeaEvent.TagSelected(it))
                    })
            }
        }
    }
}
