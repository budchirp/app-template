package me.budchirp.app.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import me.budchirp.app.repositories.SettingsData
import me.budchirp.app.ui.viewmodels.MainViewModel
import me.budchirp.app.ui.views.home.HomeView
import me.budchirp.app.ui.views.settings.GeneralSettingsView
import me.budchirp.app.ui.views.settings.LanguagesView
import me.budchirp.app.ui.views.settings.SettingsView
import me.budchirp.app.ui.views.settings.about.AboutView
import me.budchirp.app.ui.views.settings.about.LicensesView

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    settingsData: SettingsData
) {
  Box(
      modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background),
  ) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.destination,
        enterTransition = { fadeIn(tween(150)) },
        exitTransition = { fadeOut(tween(150)) },
    ) {
      animatedComposable(route = Route.Home.destination) { HomeView(navController = navController) }

      navigation(
          startDestination = Route.Settings.destination,
          route = "_${Route.Settings.destination}",
      ) {
        animatedComposable(route = Route.Settings.destination) {
          SettingsView(navController = navController)
        }
        animatedComposable(route = Route.GeneralSettings.destination) {
          GeneralSettingsView(
              navController = navController,
              viewModel = viewModel,
              settingsData = settingsData
          )
        }

        animatedComposable(route = Route.Languages.destination) {
          LanguagesView(
              navController = navController,
          )
        }

        navigation(
            startDestination = Route.About.destination,
            route = "_${Route.About.destination}"
        ) {
          animatedComposable(route = Route.About.destination) {
            AboutView(navController = navController)
          }
          animatedComposable(route = Route.Licenses.destination) {
            LicensesView(navController = navController)
          }
        }
      }
    }
  }
}
