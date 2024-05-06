@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import me.budchirp.app.R
import me.budchirp.app.ui.motion.MotionConstants
import me.budchirp.app.ui.navigation.mainRoutes

operator fun PaddingValues.minus(other: PaddingValues): PaddingValues {
    return PaddingValues(
        top = this.calculateTopPadding(),
        bottom = other.calculateBottomPadding()
    )
}

@Composable
fun AppTopAppBar(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    showBack: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {}
) {
    LargeTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun AppBottomAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route ?: "home"

    AnimatedVisibility(
        visible = mainRoutes.any { route -> route.destination == currentDestination },
        enter = fadeIn(
            animationSpec = tween(durationMillis = MotionConstants.DefaultMotionDuration)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = MotionConstants.DefaultMotionDuration)
        )
    ) {
        NavigationBar {
            mainRoutes.map { route ->
                val selected =
                    currentDestination.contains(route.destination, ignoreCase = true)

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(
                                route = route.destination,
                            )
                        }
                    },
                    label = { Text(text = stringResource(id = route.title)) },
                    icon = {
                        Crossfade(
                            targetState = selected,
                            animationSpec = tween(
                                durationMillis = MotionConstants.DefaultMotionDuration
                            )
                        ) { selected ->
                            Icon(icon = if (selected) route.icon else route.unselectedIcon)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppLayout(
    navController: NavHostController,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        bottomBar = {
            AppBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        content(innerPadding = innerPadding)
    }
}

@Composable
fun Layout(
    navController: NavHostController,
    title: String,
    innerPadding: PaddingValues = PaddingValues(
        top = 0.dp,
        bottom = 0.dp
    ),
    showBack: Boolean = true,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = rememberTopAppBarState(),
            canScroll = { true }
        )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route ?: "home"


    Scaffold(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopAppBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                title = title,
                showBack = showBack,
                actions = actions
            )
        }
    ) { _innerPadding ->
        Column(
            modifier =
            modifier
                .fillMaxSize()
                .padding(paddingValues = if (mainRoutes.any { route -> route.destination == currentDestination }) _innerPadding - innerPadding else _innerPadding),
        ) { content() }
    }
}