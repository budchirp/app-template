package me.budchirp.app.android.ui.views.settings.appearance

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.budchirp.app.android.R
import me.budchirp.app.android.ui.composables.Icon
import me.budchirp.app.android.ui.composables.ListItem
import me.budchirp.app.android.ui.navigation.Route
import me.budchirp.app.android.ui.navigation.appearanceRoutes

@Composable
fun AppearanceView(navController: NavHostController) {
    val isAndroid12Available = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val materialYouDesc =
        if (isAndroid12Available) R.string.appearance_material_you_desc else R.string.appearance_material_you_desc_unsupported


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(appearanceRoutes) { route ->
            ListItem(
                title = stringResource(id = route.title),
                description = stringResource(
                    id = if (route.destination == Route.MaterialYou.destination) materialYouDesc else route.description
                ),
                onClick = {
                    if (route.destination != Route.MaterialYou.destination) {
                        navController.navigate(route = route.destination)
                    }
                },
                firstItem = {
                    Icon(
                        icon = route.icon
                    )
                },
                enabled = if (route.destination == Route.MaterialYou.destination) isAndroid12Available else true
            )
        }
    }
}