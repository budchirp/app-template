package me.budchirp.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import me.budchirp.app.ui.composables.animatedComposable
import me.budchirp.app.ui.views.HomeView
import me.budchirp.app.ui.views.settings.GeneralSettingsView
import me.budchirp.app.ui.views.settings.LanguagesView
import me.budchirp.app.ui.views.settings.SettingsView
import me.budchirp.app.ui.views.settings.about.AboutView
import me.budchirp.app.ui.views.settings.about.LicensesView
import me.budchirp.app.ui.views.settings.appearance.AppearanceView
import me.budchirp.app.ui.views.settings.appearance.MaterialYouView
import me.budchirp.app.ui.views.settings.appearance.ThemeView

@Composable
fun AppNavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.destination
    ) {
        animatedComposable(route = Route.Home.destination) {
            HomeView(
                navController = navController,
                innerPadding = innerPadding
            )
        }

        navigation(
            startDestination = Route.Settings.destination,
            route = "_${Route.Settings.destination}"
        ) {
            animatedComposable(route = Route.Settings.destination) {
                SettingsView(navController = navController, innerPadding = innerPadding)
            }

            animatedComposable(route = Route.GeneralSettings.destination) {
                GeneralSettingsView(
                    navController = navController
                )
            }

            animatedComposable(route = Route.Languages.destination) {
                LanguagesView(
                    navController = navController
                )
            }

            navigation(
                startDestination = Route.Appearance.destination,
                route = "_${Route.Appearance.destination}"
            ) {
                animatedComposable(route = Route.Appearance.destination) {
                    AppearanceView(navController = navController)
                }
                animatedComposable(route = Route.Theme.destination) {
                    ThemeView(navController = navController)
                }
                animatedComposable(route = Route.MaterialYou.destination) {
                    MaterialYouView(navController = navController)
                }
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