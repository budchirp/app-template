package me.budchirp.app.android.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import kotlinx.coroutines.CoroutineScope
import me.budchirp.app.android.ui.composables.animatedComposableBuilder
import me.budchirp.app.android.ui.views.HomeView
import me.budchirp.app.android.ui.views.settings.GeneralSettingsView
import me.budchirp.app.android.ui.views.settings.LanguagesView
import me.budchirp.app.android.ui.views.settings.SettingsView
import me.budchirp.app.android.ui.views.settings.about.AboutView
import me.budchirp.app.android.ui.views.settings.about.LicensesView
import me.budchirp.app.android.ui.views.settings.appearance.AppearanceView
import me.budchirp.app.android.ui.views.settings.appearance.MaterialYouView
import me.budchirp.app.android.ui.views.settings.appearance.ThemeView

@Composable
fun AppNavGraph(
    navController: NavHostController,
    drawerState: DrawerState,
    drawerScope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.destination
    ) {
        val animatedComposable = animatedComposableBuilder(
            navController = navController,
            drawerState = drawerState,
            drawerScope = drawerScope
        )

        animatedComposable(
            Route.Home.destination,
            null,
            null
        ) {
            HomeView(
            )
        }

        navigation(
            startDestination = Route.Settings.destination,
            route = "_${Route.Settings.destination}"
        ) {
            animatedComposable(
                Route.Settings.destination,
                null,
                null
            ) {
                SettingsView(navController = navController)
            }

            animatedComposable(
                Route.GeneralSettings.destination,
                null,
                null
            ) {
                GeneralSettingsView(
                )
            }

            animatedComposable(
                Route.Languages.destination,
                null,
                null
            ) {
                LanguagesView(
                )
            }

            navigation(
                startDestination = Route.Appearance.destination,
                route = "_${Route.Appearance.destination}"
            ) {
                animatedComposable(
                    Route.Appearance.destination,
                    null,
                    null
                ) {
                    AppearanceView(navController = navController)
                }
                animatedComposable(
                    Route.Theme.destination,
                    null,
                    null
                ) {
                    ThemeView()
                }
                animatedComposable(
                    Route.MaterialYou.destination,
                    null,
                    null
                ) {
                    MaterialYouView()
                }
            }

            navigation(
                startDestination = Route.About.destination,
                route = "_${Route.About.destination}"
            ) {
                animatedComposable(
                    Route.About.destination,
                    null,
                    null
                ) {
                    AboutView(navController = navController)
                }
                animatedComposable(
                    Route.Licenses.destination,
                    null,
                    null
                ) {
                    LicensesView()
                }
            }
        }
    }
}