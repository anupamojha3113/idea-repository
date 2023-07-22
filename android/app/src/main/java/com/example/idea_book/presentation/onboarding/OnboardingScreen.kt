package com.example.idea_book.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.idea_book.ui.theme.IdeaBookTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.idea_book.R
import com.example.idea_book.domain.model.OnBoardingPageData
import com.example.idea_book.presentation.destinations.IdeasScreenDestination
import com.example.idea_book.presentation.onboarding.components.OnBoardingPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Destination
@Composable
fun OnBoardingScreen(
    navigator: DestinationsNavigator? = null
) {
    val pagerState = rememberPagerState(initialPage = 0)

    val pages = listOf<OnBoardingPageData>(
        OnBoardingPageData(
            title = "Welcome to Idea Book",
            description = "Idea Book is a place where you can share your ideas with the world or just keep them for yourself and come back to them later.",
            image = R.drawable.idea
        ),
        OnBoardingPageData(
            title = "Comment on other people's ideas",
            description = "You can comment on other people's ideas and give them feedback. You can also up-vote or down-vote them!",
            image = R.drawable.onboarding
        ),
        OnBoardingPageData(
            title = "Share your ideas and collaborate",
            description = "You can share your ideas with the world and collaborate with other people on them to create something great!",
            image = R.drawable.idea
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(count = pages.size, state = pagerState) {
                OnBoardingPage(pageData = pages[it])
            }
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                activeColor = MaterialTheme.colors.primary,
            )
            AnimatedVisibility(visible = pagerState.currentPage == 2) {
                OutlinedButton(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(horizontal = 8.dp),
                    onClick = { navigator?.navigate(IdeasScreenDestination()) },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                ) {
                    Text(text = "Get Started")
                }
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    IdeaBookTheme {
        OnBoardingScreen()
    }
}
