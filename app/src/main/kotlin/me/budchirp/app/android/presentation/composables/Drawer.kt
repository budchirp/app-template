package me.budchirp.app.android.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.budchirp.app.android.R
import me.budchirp.app.android.presentation.navigation.Route
import me.budchirp.app.android.presentation.navigation.mainRoutes
import me.budchirp.app.android.presentation.utils.getSystemRoundedCorners

@Composable
fun Drawer(
    navController: NavController,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    drawerScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable (DrawerState, CoroutineScope) -> Unit,
) {
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT, WindowWidthSizeClass.MEDIUM ->
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet(
                        modifier =
                            Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(fraction = 0.85f)
                                .clip(shape = RoundedCornerShape(size = getSystemRoundedCorners())),
                        drawerState = drawerState,
                    ) {
                        DrawerContent(
                            navController = navController,
                            drawerState = drawerState,
                            drawerScope = drawerScope,
                        )
                    }
                },
                gesturesEnabled = true,
                drawerState = drawerState,
            ) {
                content(drawerState, drawerScope)
            }

        WindowWidthSizeClass.EXPANDED ->
            PermanentNavigationDrawer(drawerContent = {
                PermanentDrawerSheet(
                    modifier =
                        Modifier.fillMaxHeight().fillMaxWidth(fraction = 0.35f).clip(
                            shape =
                                RoundedCornerShape(
                                    size = 0.dp,
                                ),
                        ),
                    drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                ) {
                    DrawerContent(navController = navController)
                }
            }) {
                content(drawerState, drawerScope)
            }

        else -> content(drawerState, drawerScope)
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    drawerState: DrawerState? = null,
    drawerScope: CoroutineScope? = null,
) {
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

        if (drawerState != null && drawerScope != null) {
            IconButton(onClick = {
                drawerScope.launch {
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
        val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
        val currentDestination: String =
            navBackStackEntry?.destination?.route ?: Route.Home.destination

        mainRoutes.map { route: Route ->
            val selected: Boolean =
                currentDestination.contains(route.destination, ignoreCase = true)

            NavigationDrawerItem(
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                selected = selected,
                onClick = {
                    if (currentDestination != route.destination) {
                        if (drawerState != null && drawerScope != null) {
                            drawerScope.launch {
                                drawerState.apply {
                                    close()
                                }
                            }
                        }

                        navController.navigate(
                            route = route.destination,
                        )
                    }
                },
                label = { Text(text = stringResource(id = route.title)) },
                icon = {
                    Icon(icon = if (selected) route.icon else route.unselectedIcon)
                },
            )
        }
    }
}