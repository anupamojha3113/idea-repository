package com.example.idea_book.presentation.ideas.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.idea_book.core.utils.timeAgo
import com.example.idea_book.domain.model.IdeaModel
import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.presentation.common.Chip
import com.example.idea_book.presentation.common.TagList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IdeaItem(
    idea: IdeaModel,
    selectedTags: List<TagModel>,
    liked: Boolean,
    onIdeaClick: () -> Unit,
    onLikeClick: (ideaId: Int, ideaLiked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var initialLike by rememberSaveable { mutableStateOf(liked) }
    var ideaLiked by rememberSaveable {
        mutableStateOf(liked)
    }
    var likes by rememberSaveable {
        mutableStateOf(idea.likes_total)
    }
    val scope = rememberCoroutineScope()
    var debounceJob: Job? by remember {
        mutableStateOf(null)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onIdeaClick)
                .padding(16.dp)
                .padding(end = 2.dp)
        ) {
            TagList(tags = idea.tags, selectedTags = selectedTags)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = idea.title,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = idea.username,
                style = MaterialTheme.typography.body2.copy(
                    fontStyle = FontStyle.Italic,
                    fontWeight = MaterialTheme.typography.h6.fontWeight
                ),

                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = idea.description,
                style = MaterialTheme.typography.body1,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = idea.created_at.timeAgo(),
                    style = MaterialTheme.typography.body2.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = MaterialTheme.typography.h6.fontWeight,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Chip(text = "👍 $likes", active = ideaLiked) {
                    ideaLiked = !ideaLiked
                    if (ideaLiked) {
                        likes++
                    } else {
                        likes--
                    }
                    debounceJob?.cancel()
                    debounceJob = scope.launch {
                        delay(800)
                        if (initialLike != ideaLiked) {
                            onLikeClick(idea.id, ideaLiked)
                            initialLike = ideaLiked
                        }
                    }
                }
            }
        }
    }
}
