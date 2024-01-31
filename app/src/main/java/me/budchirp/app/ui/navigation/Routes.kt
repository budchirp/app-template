package me.budchirp.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector
import me.budchirp.app.R

sealed class Route(
    val destination: String,
    val label: Int,
    val description: Int = R.string.empty,
    val icon: ImageVector,
) {
  object Home :
      Route(
          destination = "home",
          label = R.string.app_name,
          icon = Icons.Filled.Home,
      )

  object Settings :
      Route(
          destination = "settings",
          label = R.string.settings,
          icon = Icons.Filled.Settings,
      )

  object GeneralSettings :
      Route(
          destination = "settings/general",
          label = R.string.general_settings,
          description = R.string.general_settings_desc,
          icon = Icons.Filled.Settings,
      )

  object Languages :
      Route(
          destination = "settings/languages",
          label = R.string.languages,
          description = R.string.languages_desc,
          icon = Icons.Filled.Translate,
      )

  object About :
      Route(
          destination = "settings/about",
          label = R.string.about,
          description = R.string.about_desc,
          icon = Icons.Filled.Info,
      )

  object Licenses :
      Route(
          destination = "settings/about/licenses",
          label = R.string.licenses,
          description = R.string.licenses_desc,
          icon = Icons.Filled.Gavel,
      )
}

val mainRoutes = listOf(Route.Home, Route.Settings)
val settingRoutes = listOf(Route.GeneralSettings, Route.Languages, Route.About)
val aboutRoutes = listOf(Route.Licenses)

val allRoutes = mainRoutes + settingRoutes + aboutRoutes
