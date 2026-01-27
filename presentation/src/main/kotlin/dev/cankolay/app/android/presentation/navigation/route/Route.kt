package dev.cankolay.app.android.presentation.navigation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import dev.cankolay.app.android.presentation.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Home : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data object Appearance : Route

    @Serializable
    data object Theme : Route

    @Serializable
    data object MaterialYou : Route

    @Serializable
    data object Languages : Route

    @Serializable
    data object About : Route
}

@Composable
fun NavKey.getDetails(): RouteDetail {
    val routeDetail = mapOf(
        Route.Home to RouteDetail(
            title = stringResource(id = R.string.home),
            description = stringResource(id = R.string.home),
            icon = RouteDetailIcon(
                default = Icons.Filled.Home,
                outlined = Icons.Outlined.Home,
            )
        ),
        Route.Settings to RouteDetail(
            title = stringResource(id = R.string.settings),
            description = stringResource(id = R.string.settings),
            icon = RouteDetailIcon(
                default = Icons.Filled.Settings,
                outlined = Icons.Outlined.Settings
            )
        ),
        Route.Appearance to RouteDetail(
            title = stringResource(id = R.string.appearance),
            description = stringResource(id = R.string.appearance_desc),
            icon = RouteDetailIcon(
                default = Icons.Default.Palette,
            )
        ),
        Route.Theme to RouteDetail(
            title = stringResource(id = R.string.theme),
            description = stringResource(id = R.string.theme_desc),
            icon = RouteDetailIcon(
                default = Icons.Default.DarkMode,
            ),
        ),
        Route.MaterialYou to RouteDetail(
            title = stringResource(id = R.string.material_you),
            description = stringResource(id = R.string.material_you_desc),
            icon = RouteDetailIcon(
                default = Icons.Default.Colorize,
            )
        ),
        Route.Languages to RouteDetail(
            title = stringResource(id = R.string.languages),
            description = stringResource(id = R.string.languages_desc),
            icon = RouteDetailIcon(
                default = Icons.Filled.Translate,
            )
        ),
        Route.About to RouteDetail(
            title = stringResource(id = R.string.about),
            description = stringResource(id = R.string.about_desc),
            icon = RouteDetailIcon(
                default = Icons.Filled.Info,
            )
        ),
    )

    return routeDetail[this]!!
}