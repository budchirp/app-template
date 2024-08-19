package me.budchirp.app.android.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppLayout(content: @Composable () -> Unit) {
    Surface(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Drawer {
            content()
        }
    }
}