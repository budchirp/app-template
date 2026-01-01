package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowSizeClass
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composition.LocalDrawerState
import dev.cankolay.app.android.presentation.composition.LocalNavController
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.routeDetails
import dev.cankolay.app.android.presentation.util.UiUtil
import kotlinx.coroutines.launch
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

val routes = listOf(
    Route.Home, Route.Settings
)

@Composable
fun AppDrawer(
    content: @Composable () -> Unit,
) {
    val drawerState = LocalDrawerState.current

    if (currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = 0.35f),
                drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            ) {
                DrawerContent()
            }
        }) {
            content()
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(fraction = 0.80f)
                            .clip(
                                shape =
                                    SquircleShape(
                                        radius = UiUtil.getSystemRoundedCorners(),
                                        smoothing = CornerSmoothing.Small
                                    ),
                            ),
                    drawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    drawerState = drawerState,
                ) {
                    DrawerContent()
                }
            },
            gesturesEnabled = true,
            drawerState = drawerState,
        ) {
            content()
        }
    }
}

@Composable
fun DrawerContent() {
    val navController = LocalNavController.current
    val drawerState = LocalDrawerState.current

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 64.dp)
                .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
        )

        if (!currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(widthDpBreakpoint = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)) {
            IconButton(onClick = {
                coroutineScope.launch {
                    drawerState.apply {
                        close()
                    }
                }
            }) {
                Icon(
                    icon = Icons.Filled.Close,
                )
            }
        }
    }

    Column(
        modifier =
            Modifier
                .padding(vertical = 16.dp)
                .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()

        routes.map { route ->
            val details = routeDetails[route]!!

            val isSelected =
                backStackEntry?.destination?.hasRoute(route = route::class) == true

            NavigationDrawerItem(
                modifier = Modifier.padding(paddingValues = NavigationDrawerItemDefaults.ItemPadding),
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        coroutineScope.launch {
                            drawerState.apply {
                                close()
                            }
                        }

                        navController.navigate(
                            route = route,
                        )
                    }
                },
                label = { Text(text = stringResource(id = details.title)) },
                icon = {
                    Icon(
                        icon = if (isSelected) details.icon.default else details.icon.outlined
                            ?: details.icon.default
                    )
                },
            )
        }
    }
}