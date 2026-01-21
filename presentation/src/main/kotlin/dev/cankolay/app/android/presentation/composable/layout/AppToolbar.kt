package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.AppBarRow
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarDefaults.ScreenOffset
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composition.LocalNavBackStack
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.RouteDetail
import dev.cankolay.app.android.presentation.navigation.getDetails

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.AppToolbar() {
    val navBackStack = LocalNavBackStack.current

    val route = navBackStack.lastOrNull() ?: Route.Home
    val isVisible = routes.any {
        it == route
    }

    AnimatedVisibility(
        modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -ScreenOffset - 16.dp)
                .zIndex(1f), visible = isVisible, enter = fadeIn() + scaleIn(), exit = fadeOut()
    ) {
        HorizontalFloatingToolbar(
            colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
            expanded = false,
        ) {
            data class AppRowRoute(
                val instance: Route,
                val details: RouteDetail
            )

            val appRowRoutes = mutableListOf<AppRowRoute>()

            routes.forEach { route ->
                appRowRoutes.add(
                    element = AppRowRoute(
                        instance = route,
                        details = route.getDetails()
                    )
                )
            }

            AppBarRow {
                appRowRoutes.map { route ->
                    val isSelected =
                        navBackStack.lastOrNull() == route.instance

                    clickableItem(
                        label = route.details.title,
                        icon = {
                            Icon(
                                icon = if (isSelected) route.details.icon.default else route.details.icon.outlined
                                    ?: route.details.icon.default
                            )
                        },
                        onClick = {
                            if (!isSelected) {
                                navBackStack.clear()

                                navBackStack.add(
                                    element = route.instance
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}