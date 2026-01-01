package dev.cankolay.app.android.presentation.view.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composition.LocalNavController
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.routeDetails

val routes =
    listOf(Route.Appearance, Route.Languages, Route.About)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView() {
    val navController = LocalNavController.current

    AppLayout(route = Route.Settings) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = routes) { route ->
                val details = routeDetails[route]!!

                ListItem(
                    title = stringResource(id = details.title),
                    description = stringResource(id = details.description),
                    onClick = {
                        navController.navigate(route = route)
                    },
                    leadingIcon = {
                        Icon(
                            icon = details.icon.default,
                        )
                    },
                )
            }
        }
    }
}