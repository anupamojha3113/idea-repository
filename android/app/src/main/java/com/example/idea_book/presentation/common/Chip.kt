package com.example.idea_book.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.idea_book.ui.theme.BlueBorder
import com.example.idea_book.ui.theme.BlueTint

@Composable
fun Chip(
    text: String,
    active: Boolean = false,
    icon: String = "",
    onClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(end = 16.dp),
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .background(
                    color = if (active) {
                        BlueTint
                    } else {
                        MaterialTheme.colors.surface
                    }
                )
                .border(
                    width = 1.dp,
                    color = if (active) {
                        BlueBorder
                    } else {
                        MaterialTheme.colors.onSurface
                    },
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 15.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(icon)
                        .build(),
                    contentDescription = text,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
            )
        }
    }
}
