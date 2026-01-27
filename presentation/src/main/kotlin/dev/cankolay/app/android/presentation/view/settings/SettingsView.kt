package dev.cankolay.app.android.presentation.view.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.cankolay.app.android.presentation.composable.CardStackList
import dev.cankolay.app.android.presentation.composable.CardStackListItem
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn
import dev.cankolay.app.android.presentation.composition.LocalNavBackStack
import dev.cankolay.app.android.presentation.navigation.route.Route
import dev.cankolay.app.android.presentation.navigation.route.getDetails

val routes =
    listOf(Route.Appearance, Route.Languages, Route.About)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView() {
    val navBackStack = LocalNavBackStack.current

    AppLayout(route = Route.Settings) {
        AppLazyColumn {
            item {
                CardStackList(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    items = routes.map { route ->
                        val details = route.getDetails()

                        CardStackListItem(
                            title = details.title,
                            description = details.description,
                            onClick = {
                                navBackStack.add(element = route)
                            },
                            leadingContent = {
                                Icon(
                                    icon = details.icon.default,
                                )
                            },
                        )
                    }
                )
            }
        }
    }
}