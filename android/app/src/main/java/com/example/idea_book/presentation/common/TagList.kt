package com.example.idea_book.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.idea_book.domain.model.TagModel
import com.example.idea_book.ui.theme.BlueBorder
import com.example.idea_book.ui.theme.BlueTint

@Composable
fun TagList(
    modifier: Modifier = Modifier,
    tags: List<TagModel>,
    selectedTags: List<TagModel> = emptyList(),
    onTagSelected: (TagModel) -> Unit = {},
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        items(tags) { tag ->
            Chip(text = tag.name, active = selectedTags.contains(tag), icon = tag.icon) {
                onTagSelected(tag)
            }

//            Card(
//                shape = RoundedCornerShape(8.dp),
//                modifier = Modifier.padding(end = 16.dp),
//                elevation = 8.dp,
//            ) {
//                Row(
//                    modifier = Modifier
//                        .clickable { onTagSelected(tag) }
//                        .background(
//                            color = if (selectedTags.contains(tag)) {
//                                BlueTint
//                            } else {
//                                MaterialTheme.colors.surface
//                            }
//                        )
//                        .border(
//                            width = 1.dp,
//                            color = if (selectedTags.contains(tag)) {
//                                BlueBorder
//                            } else {
//                                MaterialTheme.colors.onSurface
//                            },
//                            shape = RoundedCornerShape(8.dp)
//                        )
//                        .padding(horizontal = 15.dp, vertical = 4.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    if (tag.icon.isNotEmpty()) {
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(tag.icon)
//                                .build(),
//                            contentDescription = tag.name,
//                            modifier = Modifier
//                                .size(20.dp)
//                                .clip(CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                    }
//                    Text(
//                        text = tag.name,
//                    )
//                }
//            }
        }
    }
}
