package dev.cankolay.app.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import dev.cankolay.app.android.presentation.composable.AnimationType
import dev.cankolay.app.android.presentation.composable.animatedComposable
import dev.cankolay.app.android.presentation.composition.LocalNavController
import dev.cankolay.app.android.presentation.view.HomeView
import dev.cankolay.app.android.presentation.view.settings.AboutView
import dev.cankolay.app.android.presentation.view.settings.LanguagesView
import dev.cankolay.app.android.presentation.view.settings.SettingsView
import dev.cankolay.app.android.presentation.view.settings.appearance.AppearanceView
import dev.cankolay.app.android.presentation.view.settings.appearance.MaterialYouView
import dev.cankolay.app.android.presentation.view.settings.appearance.ThemeView

@Composable
fun AppNavGraph() {
    NavHost(
        navController = LocalNavController.current,
        startDestination = Route.Home,
    ) {
        animatedComposable<Route.Home>(animationType = AnimationType.FADE) {
            HomeView()
        }

        animatedComposable<Route.Settings>(animationType = AnimationType.FADE) {
            SettingsView()
        }

        animatedComposable<Route.Languages> {
            LanguagesView()
        }

        animatedComposable<Route.Appearance> {
            AppearanceView()
        }
        animatedComposable<Route.Theme> {
            ThemeView()
        }

        animatedComposable<Route.MaterialYou> {
            MaterialYouView()
        }

        animatedComposable<Route.About> {
            AboutView()
        }
    }
}