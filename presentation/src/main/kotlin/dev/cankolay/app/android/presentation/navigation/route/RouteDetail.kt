package dev.cankolay.app.android.presentation.navigation.route

import androidx.compose.ui.graphics.vector.ImageVector

data class RouteDetailIcon(
    val default: ImageVector,
    val outlined: ImageVector? = null
)

data class RouteDetail(
    val title: String,
    val description: String,

    val icon: RouteDetailIcon
)