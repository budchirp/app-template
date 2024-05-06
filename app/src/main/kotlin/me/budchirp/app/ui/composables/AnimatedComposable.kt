package me.budchirp.app.ui.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import me.budchirp.app.ui.motion.slideIn
import me.budchirp.app.ui.motion.slideOut

const val initialOffset = 0.10f

fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = { slideIn(initialOffsetX = { (it * initialOffset).toInt() }) },
        exitTransition = { slideOut(targetOffsetX = { -(it * initialOffset).toInt() }) },
        popEnterTransition = { slideIn(initialOffsetX = { -(it * initialOffset).toInt() }) },
        popExitTransition = { slideOut(targetOffsetX = { (it * initialOffset).toInt() }) },
        content = content
    )
}