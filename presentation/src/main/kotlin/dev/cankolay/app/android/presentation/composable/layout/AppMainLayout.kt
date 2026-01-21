package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass

@Composable
fun AppMainLayout(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) {
        if (currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(
                widthDpBreakpoint = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
            )
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                AppNavigationRail()

                content()
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                AppToolbar()

                content()
            }
        }
    }
}