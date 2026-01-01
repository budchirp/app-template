package dev.cankolay.app.android.presentation.navigation

import androidx.annotation.StringRes
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
import androidx.compose.ui.graphics.vector.ImageVector
import dev.cankolay.app.android.presentation.R

data class RouteDetailsIcon(
    val default: ImageVector,
    val outlined: ImageVector? = null
)

data class RouteDetails(
    @StringRes val title: Int,
    @StringRes val description: Int,

    val icon: RouteDetailsIcon
)

val routeDetails = mapOf(
    Route.Home to RouteDetails(
        title = R.string.home,
        description = R.string.home,
        icon = RouteDetailsIcon(
            default = Icons.Filled.Home,
            outlined = Icons.Outlined.Home,
        )
    ),
    Route.Settings to RouteDetails(
        title = R.string.settings,
        description = R.string.settings,
        icon = RouteDetailsIcon(
            default = Icons.Filled.Settings,
            outlined = Icons.Outlined.Settings
        )
    ),
    Route.Appearance to RouteDetails(
        title = R.string.appearance,
        description = R.string.appearance_desc,
        icon = RouteDetailsIcon(
            default = Icons.Default.Palette,
        )
    ),
    Route.Theme to RouteDetails(
        title = R.string.appearance_theme,
        description = R.string.appearance_theme_desc,
        icon = RouteDetailsIcon(
            default = Icons.Default.DarkMode,
        ),
    ),
    Route.MaterialYou to RouteDetails(
        title = R.string.appearance_material_you,
        description = R.string.appearance_material_you_desc,
        icon = RouteDetailsIcon(
            default = Icons.Default.Colorize,
        )
    ),
    Route.Languages to RouteDetails(
        title = R.string.languages,
        description = R.string.languages_desc,
        icon = RouteDetailsIcon(
            default = Icons.Filled.Translate,
        )
    ),
    Route.About to RouteDetails(
        title = R.string.about,
        description = R.string.about_desc,
        icon = RouteDetailsIcon(
            default = Icons.Filled.Info,
        )
    ),
)