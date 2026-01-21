package dev.cankolay.app.android.presentation.composition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import dev.cankolay.app.android.presentation.navigation.Route

val LocalNavBackStack =
    staticCompositionLocalOf<NavBackStack<NavKey>> { error("Not provided") }

@Composable
fun ProvideNavBackStack(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        value = LocalNavBackStack provides rememberNavBackStack(Route.Home),
    ) {
        content()
    }
}