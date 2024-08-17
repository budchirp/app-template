package me.budchirp.app.android.presentation.views.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.budchirp.app.android.presentation.composables.Icon
import me.budchirp.app.android.presentation.composables.ListItem
import me.budchirp.app.android.presentation.navigation.Route
import me.budchirp.app.android.presentation.navigation.settingRoutes

@Composable
fun SettingsView(navController: NavHostController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(settingRoutes) { route: Route ->
            ListItem(
                title = stringResource(id = route.title),
                description = stringResource(id = route.description),
                onClick = {
                    navController.navigate(route = route.destination)
                },
                firstItem = {
                    Icon(
                        icon = route.icon,
                    )
                },
            )
        }
    }
}