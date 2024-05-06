package me.budchirp.app.ui.views.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.budchirp.app.ui.composables.Icon
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.navigation.settingRoutes

@Composable
fun SettingsView(navController: NavHostController, innerPadding: PaddingValues) {
    Layout(
        navController = navController,
        title = stringResource(id = Route.Settings.title),
        innerPadding = innerPadding,
        showBack = false
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(settingRoutes) { route ->
                ListItem(
                    title = stringResource(id = route.title),
                    description = stringResource(id = route.description),
                    firstItem = {
                        Icon(
                            icon = route.icon
                        )
                    }
                ) {
                    navController.navigate(route = route.destination)
                }
            }
        }
    }
}