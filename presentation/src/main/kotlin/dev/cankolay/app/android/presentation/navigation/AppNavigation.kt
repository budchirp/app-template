package dev.cankolay.app.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.cankolay.app.android.presentation.composition.LocalNavBackStack
import dev.cankolay.app.android.presentation.motion.TransitionType
import dev.cankolay.app.android.presentation.motion.navigationTransition
import dev.cankolay.app.android.presentation.navigation.route.Route
import dev.cankolay.app.android.presentation.view.HomeView
import dev.cankolay.app.android.presentation.view.settings.AboutView
import dev.cankolay.app.android.presentation.view.settings.LanguagesView
import dev.cankolay.app.android.presentation.view.settings.SettingsView
import dev.cankolay.app.android.presentation.view.settings.appearance.AppearanceView
import dev.cankolay.app.android.presentation.view.settings.appearance.MaterialYouView
import dev.cankolay.app.android.presentation.view.settings.appearance.ThemeView

@Composable
fun AppNavigation() {
    val navBackStack = LocalNavBackStack.current

    NavDisplay(
        backStack = navBackStack,
        onBack = {
            navBackStack.removeLastOrNull()
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Home>(metadata = navigationTransition(type = TransitionType.FADE)) {
                HomeView()
            }

            entry<Route.Settings>(metadata = navigationTransition(type = TransitionType.FADE)) {
                SettingsView()
            }

            entry<Route.Languages>(metadata = navigationTransition()) {
                LanguagesView()
            }

            entry<Route.Appearance>(metadata = navigationTransition()) {
                AppearanceView()
            }

            entry<Route.Theme>(metadata = navigationTransition()) {
                ThemeView()
            }

            entry<Route.MaterialYou>(metadata = navigationTransition()) {
                MaterialYouView()
            }

            entry<Route.About>(metadata = navigationTransition()) {
                AboutView()
            }
        }
    )
}