package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composition.LocalDrawerState
import dev.cankolay.app.android.presentation.composition.LocalNavController
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.mainRoutes
import dev.cankolay.app.android.presentation.navigation.uiRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    navController: NavHostController = LocalNavController.current,
    drawerState: DrawerState = LocalDrawerState.current,
    route: Route,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    val uiRoute = uiRoutes[route::class]!!

    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(id = uiRoute.title),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            actions()
        },
        navigationIcon = {
            navigationIcon()

            var showBack = false
            var showMenu = true
            run stop@{
                mainRoutes.forEach { mainRoute: Route ->
                    if (route == mainRoute) {
                        showBack = false
                        showMenu = true

                        return@stop
                    } else {
                        showBack = true
                        showMenu = false
                    }
                }
            }

            if (currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(
                    widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND
                )
            ) {
                showMenu =
                    false
            }

            if (showBack) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                    )
                }
            }

            if (showMenu) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(
                        icon = Icons.Filled.Menu,
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}