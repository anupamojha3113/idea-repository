package com.example.idea_book.presentation.ideas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.idea_book.presentation.common.BlankScreen
import com.example.idea_book.presentation.common.TagList
import com.example.idea_book.presentation.common.layout.Layout
import com.example.idea_book.presentation.destinations.CreateIdeaScreenDestination
import com.example.idea_book.presentation.destinations.IdeaScreenDestination
import com.example.idea_book.presentation.ideas.components.IdeaItem
import com.example.idea_book.presentation.ideas.components.NoContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun IdeasScreen(
    navigator: DestinationsNavigator,
    viewModel: IdeasViewModel = hiltViewModel(),
    myIdeas: Boolean = false
) {
    val state = viewModel.state
    val userId = viewModel.userId

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collectLatest {
            when (it) {
                is IdeasViewModel.UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.msg,
                    )
                }
            }
        }
    }

    Layout(
        navigator = navigator,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(CreateIdeaScreenDestination()) }) {
                Icon(Icons.Filled.Add, "addIcon")
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (myIdeas) {
                    item {
                        Text(
                            "Your Ideas",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                item {
                    TagList(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        tags = state.tags,
                        selectedTags = state.selectedTags
                    ) {
                        viewModel.onEvent(IdeasScreenEvent.TagSelected(it))
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = state.search,
                            onValueChange = { viewModel.onEvent(IdeasScreenEvent.SearchChanged(it)) },
                            label = { Text("Search") },
                            modifier = Modifier
                                .weight(1f),
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search"
                                )
                            },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Button(
                            onClick = { viewModel.onEvent(IdeasScreenEvent.SearchIdeas) },
                            modifier = Modifier.height(52.dp)
                        ) {
                            Text("Search")
                        }
                    }
                }
                if (state.isLoading) {
                    item {
                        BlankScreen(showLoadingSpinner = true, modifier = Modifier.height(300.dp))
                    }
                } else if (state.ideas.isEmpty()) {
                    item {
                        NoContent()
                    }
                } else {
                    items(state.ideas) {
                        IdeaItem(
                            idea = it,
                            selectedTags = state.selectedTags,
                            liked = it.likes.contains(userId),
                            onIdeaClick = { navigator.navigate(IdeaScreenDestination(it.id)) },
                            onLikeClick = viewModel::likeIdea,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        )
                    }
                }
            }
        }
    }
}
