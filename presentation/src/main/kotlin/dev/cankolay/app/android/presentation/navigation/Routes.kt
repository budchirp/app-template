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
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Home :
        Route()

    @Serializable
    data object Settings :
        Route(
        )

    @Serializable
    data object Appearance :
        Route(
        )

    @Serializable
    data object Theme :
        Route(
        )

    @Serializable
    data object MaterialYou :
        Route(
        )

    @Serializable
    data object Languages :
        Route(
        )

    @Serializable
    data object About :
        Route(
        )
}

data class UiRoute(
    @StringRes val title: Int,
    @StringRes val description: Int? = null,
    val icon: ImageVector,
    val unselectedIcon: ImageVector? = null,
)

val uiRoutes = mapOf(
    Route.Home::class to UiRoute(
        title = R.string.home,
        icon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),

    Route.Settings::class to UiRoute(
        title = R.string.settings,
        icon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    ),

    Route.Appearance::class to UiRoute(
        title = R.string.appearance,
        description = R.string.appearance_desc,
        icon = Icons.Filled.Palette
    ),

    Route.Theme::class to UiRoute(
        title = R.string.appearance_theme,
        description = R.string.appearance_theme_desc,
        icon = Icons.Filled.DarkMode
    ),

    Route.MaterialYou::class to UiRoute(
        title = R.string.appearance_material_you,
        description = R.string.appearance_material_you_desc,
        icon = Icons.Filled.Colorize
    ),

    Route.Languages::class to UiRoute(
        title = R.string.languages,
        description = R.string.languages_desc,
        icon = Icons.Filled.Translate
    ),

    Route.About::class to UiRoute(
        title = R.string.about,
        description = R.string.about_desc,
        icon = Icons.Filled.Info,
    ),

    )

val mainRoutes = listOf(Route.Home, Route.Settings)
val settingRoutes =
    listOf(Route.Appearance, Route.Languages, Route.About)
val appearanceRoutes = listOf(Route.Theme, Route.MaterialYou)
