package dev.cankolay.app.android.presentation.view.settings.appearance

import android.os.Build
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
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.navigation.appearanceRoutes
import dev.cankolay.app.android.presentation.navigation.uiRoutes

@Composable
fun AppearanceView() {
    val navController = LocalNavController.current

    val isMonet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = appearanceRoutes) { route ->
            val uiRoute = uiRoutes[route::class]!!

            ListItem(
                title = stringResource(id = uiRoute.title),
                description =
                    stringResource(
                        id =
                            if (route ==
                                Route.MaterialYou
                            ) {
                                if (isMonet) R.string.appearance_material_you_desc else R.string.appearance_material_you_desc_unsupported
                            } else {
                                uiRoute.description ?: R.string.empty
                            },
                    ),
                onClick = {
                    navController.navigate(route = route)
                },
                leadingIcon = {
                    Icon(
                        icon = uiRoute.icon,
                    )
                },
                enabled =
                    if (route ==
                        Route.MaterialYou
                    ) {
                        isMonet
                    } else {
                        true
                    },
            )
        }
    }
}