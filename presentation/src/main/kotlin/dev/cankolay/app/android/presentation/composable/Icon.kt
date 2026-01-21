package dev.cankolay.app.android.presentation.composable

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color = LocalContentColor.current
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = tint,
        modifier =
            modifier
                .size(size = 24.dp)
                .requiredSize(size = 24.dp),
    )
}

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: Painter,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        painter = icon,
        contentDescription = null,
        tint = tint,
        modifier =
            modifier
                .size(size = 24.dp)
                .requiredSize(size = 24.dp),
    )
}