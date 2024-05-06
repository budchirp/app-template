package me.budchirp.app.ui.views.settings.appearance

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.budchirp.app.R
import me.budchirp.app.ui.composables.Icon
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.navigation.appearanceRoutes

@Composable
fun AppearanceView(navController: NavHostController) {
    val isS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val materialYouDesc =
        if (isS) R.string.appearance_material_you_desc else R.string.appearance_material_you_desc_unsupported

    Layout(
        navController = navController,
        title = stringResource(id = Route.Appearance.title)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(appearanceRoutes) { route ->
                ListItem(
                    title = stringResource(id = route.title),
                    description = stringResource(
                        id = if (route.destination == Route.MaterialYou.destination) materialYouDesc else route.description
                    ),
                    enabled = if (route.destination == Route.MaterialYou.destination) isS else true,
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