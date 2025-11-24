package dev.cankolay.app.android.presentation.view.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.Icon
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composition.LocalNavController
import dev.cankolay.app.android.presentation.navigation.settingRoutes
import dev.cankolay.app.android.presentation.navigation.uiRoutes

@Composable
fun SettingsView() {
    val navController = LocalNavController.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = settingRoutes) { route ->
            val uiRoute = uiRoutes[route::class]!!

            ListItem(
                title = stringResource(id = uiRoute.title),
                description = stringResource(id = uiRoute.description ?: R.string.empty),
                onClick = {
                    navController.navigate(route = route)
                },
                leadingIcon = {
                    Icon(
                        icon = uiRoute.icon,
                    )
                },
            )
        }
    }
}