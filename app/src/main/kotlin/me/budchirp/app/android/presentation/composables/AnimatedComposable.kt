package me.budchirp.app.android.presentation.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import me.budchirp.app.android.presentation.motion.slideIn
import me.budchirp.app.android.presentation.motion.slideOut
import me.budchirp.app.android.presentation.navigation.allRoutes
import me.budchirp.app.android.presentation.navigation.findByDestination

val initialOffset: Float = 0.10f

fun NavGraphBuilder.animatedComposableBuilder(
    navController: NavHostController,
    drawerState: DrawerState,
    drawerScope: CoroutineScope,
): (
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
                    navController = navController,
                    route = allRoutes.findByDestination(destination = route),
                    drawerState = drawerState,
                    drawerScope = drawerScope,
                ) {
                    content(this, it)
                }
            },
        )
    }