package me.budchirp.app.android.ui.composables

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Icon(icon: ImageVector, modifier: Modifier = Modifier) {
    androidx.compose.material3.Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .size(size = 24.dp)
            .requiredSize(size = 24.dp)
    )
}

@Composable
fun Icon(icon: Painter, modifier: Modifier = Modifier) {
    androidx.compose.material3.Icon(
        painter = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .size(size = 24.dp)
            .requiredSize(size = 24.dp)
    )
}