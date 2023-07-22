package com.example.idea_book.presentation.common.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.idea_book.core.constants.LightBulb
import kotlinx.coroutines.launch
import com.example.idea_book.domain.model.MenuItem
import com.example.idea_book.presentation.destinations.AuthScreenDestination
import com.example.idea_book.presentation.destinations.IdeasScreenDestination
import com.example.idea_book.presentation.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun Layout(
    navigator: DestinationsNavigator,
    viewModel: LayoutViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val user = viewModel.user
    val coroutineScope = rememberCoroutineScope()

    val drawerItems: List<MenuItem> = listOf(
        MenuItem(
            title = "Home",
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
        ),
        MenuItem(
            title = "Your Ideas",
            icon = { Icon(LightBulb, contentDescription = "Your Ideas") },
        ),
        MenuItem(
            title = "Profile",
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
        )
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar("Idea Book") {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = floatingActionButton,
        drawerContent = {
            Column {
                DrawerHeader(user.displayName!!) {
                    viewModel.signOut {
                        navigator.navigate(
                            AuthScreenDestination
                        )
                    }
                }
                DrawerBody(items = drawerItems, onItemClick = { menuItem ->
                    when (menuItem.title) {
                        "Home" -> navigator.navigate(IdeasScreenDestination())
                        "Your Ideas" -> navigator.navigate(IdeasScreenDestination(true))
                        "Profile" -> navigator.navigate(ProfileScreenDestination)
                    }
                })
            }
        },
        content = { content() }
    )
}
