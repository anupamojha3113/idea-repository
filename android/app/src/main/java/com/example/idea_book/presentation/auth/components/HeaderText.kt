package com.example.idea_book.presentation.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.material.R

@Composable
fun HeaderText() {
    Text(
        text = "Welcome to IdeaBook!",
        style = MaterialTheme.typography.h4,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Please sign in to continue",
            style = MaterialTheme.typography.h6,
        )
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .width(35.dp)
                .height(35.dp)
                .background(
                    color = Color.Green,
                    shape = RoundedCornerShape(50)
                )
                .padding(1.dp)
        ) {
            Icon(
                painter = painterResource(
                    R.drawable.material_ic_keyboard_arrow_right_black_24dp
                ),
                contentDescription = "forward",
                tint = Color.Black,
                modifier = Modifier.align(Alignment.Center).size(30.dp)
            )
        }
    }
}
