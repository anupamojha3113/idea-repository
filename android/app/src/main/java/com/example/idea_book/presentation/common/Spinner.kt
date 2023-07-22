package com.example.idea_book.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    variant: SpinnerVariant = SpinnerVariant.Default,
) {
    Box(modifier = modifier) {
        when (variant) {
            SpinnerVariant.Default -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                    strokeWidth = 5.dp,
                    color = MaterialTheme.colors.primary,
                )
            }
            SpinnerVariant.Large -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp),
                    strokeWidth = 10.dp,
                    color = MaterialTheme.colors.primary,
                )
            }
            SpinnerVariant.Small -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(25.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colors.primary,
                )
            }
        }
    }
}

sealed class SpinnerVariant {
    object Default : SpinnerVariant()
    object Small : SpinnerVariant()
    object Large : SpinnerVariant()
}
