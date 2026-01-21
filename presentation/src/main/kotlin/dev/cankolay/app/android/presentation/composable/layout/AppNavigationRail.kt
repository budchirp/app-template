package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composition.LocalNavBackStack
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.getDetails

val routes = listOf(
    Route.Home, Route.Settings
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppNavigationRail() {
    val navBackStack = LocalNavBackStack.current

    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            routes.forEach { route ->
                val details = route.getDetails()

                val isSelected = (navBackStack.lastOrNull() ?: Route.Home) == route

                NavigationRailItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navBackStack.clear()

                            navBackStack.add(
                                element = route
                            )
                        }
                    },
                    icon = {
                        Icon(
                            icon = if (isSelected) details.icon.default else details.icon.outlined
                                ?: details.icon.default
                        )
                    }
                )
            }
        }
    }
}