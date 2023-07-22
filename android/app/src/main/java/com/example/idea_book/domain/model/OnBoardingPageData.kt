package com.example.idea_book.domain.model

import androidx.annotation.DrawableRes

data class OnBoardingPageData(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)
