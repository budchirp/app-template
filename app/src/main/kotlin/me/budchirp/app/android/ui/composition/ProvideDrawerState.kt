package me.budchirp.app.android.ui.composition

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

val LocalDrawerState: ProvidableCompositionLocal<DrawerState> =
    staticCompositionLocalOf<DrawerState> { error("Not provided") }

@Composable
fun ProvideDrawerState(content: @Composable () -> Unit) {
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(
        value = LocalDrawerState provides drawerState,
    ) {
        content()
    }
}