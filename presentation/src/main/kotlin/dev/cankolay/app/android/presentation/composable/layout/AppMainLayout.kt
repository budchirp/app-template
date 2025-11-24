package dev.cankolay.app.android.presentation.composable.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppMainLayout(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier =
            Modifier
                .fillMaxSize()
    ) {
        AppDrawer {
            content()
        }
    }
}