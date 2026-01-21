package dev.cankolay.app.android.presentation.theme

import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

val shapes = Shapes(
    extraSmall = SquircleShape(radius = 8.dp, smoothing = CornerSmoothing.Small),
    small = SquircleShape(radius = 12.dp, smoothing = CornerSmoothing.Small),
    medium = SquircleShape(radius = 16.dp, smoothing = CornerSmoothing.Small),
    large = SquircleShape(radius = 24.dp, smoothing = CornerSmoothing.Small),
    extraLarge = SquircleShape(radius = 32.dp, smoothing = CornerSmoothing.Small),
)