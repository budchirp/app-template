package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import dev.cankolay.app.android.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLayout(
    route: Route,
    scrollBehavior: TopAppBarScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = rememberTopAppBarState(),
            canScroll = { true },
        ),
    topBar: (@Composable (Route, TopAppBarScrollBehavior) -> Unit) = { route, scrollBehavior ->
        AppTopAppBar(
            route = route,
            scrollBehavior = scrollBehavior
        )
    },
    bottomBar: (@Composable () -> Unit) = {},
    content: @Composable () -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Horizontal),
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            topBar(route, scrollBehavior)
        },
        bottomBar = {
            bottomBar()
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding: PaddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues = innerPadding,
                    ),
        ) { content() }
    }
}