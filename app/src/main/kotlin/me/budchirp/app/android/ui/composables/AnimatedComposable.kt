package me.budchirp.app.android.ui.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.budchirp.app.android.ui.motion.slideIn
import me.budchirp.app.android.ui.motion.slideOut
import me.budchirp.app.android.ui.navigation.allRoutes
import me.budchirp.app.android.ui.navigation.findByDestination

const val initialOffset: Float = 0.10f

fun NavGraphBuilder.animatedComposableBuilder(): (
    String,
    List<NamedNavArgument>?,
    List<NavDeepLink>?,
    @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) -> Unit =
    fun(
        route: String,
        arguments: List<NamedNavArgument>?,
        deepLinks: List<NavDeepLink>?,
        content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
    ) {
        composable(
            route = route,
            arguments = arguments ?: emptyList(),
            deepLinks = deepLinks ?: emptyList(),
            enterTransition = { slideIn(initialOffsetX = { (it * initialOffset).toInt() }) },
            exitTransition = { slideOut(targetOffsetX = { -(it * initialOffset).toInt() }) },
            popEnterTransition = { slideIn(initialOffsetX = { -(it * initialOffset).toInt() }) },
            popExitTransition = { slideOut(targetOffsetX = { (it * initialOffset).toInt() }) },
            content = {
                Layout(
                    route = allRoutes.findByDestination(destination = route),
                ) {
                    content(this, it)
                }
            },
        )
    }