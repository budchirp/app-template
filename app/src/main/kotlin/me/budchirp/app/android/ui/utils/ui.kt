package me.budchirp.app.android.ui.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat

@Composable
fun getSystemRoundedCorners(): Dp {
    val context: Context = LocalContext.current

    val roundedCornerRadius: Dp =
        try {
            val resId: Int =
                context.resources.getIdentifier("rounded_corner_radius", "dimen", "android")

            if (resId > 0) {
                ResourcesCompat.getFloat(context.resources, resId).dp
            } else {
                32.dp
            }
        } catch (e: Exception) {
            32.dp
        }

    return roundedCornerRadius
}