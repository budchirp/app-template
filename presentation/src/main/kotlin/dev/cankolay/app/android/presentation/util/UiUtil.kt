package dev.cankolay.app.android.presentation.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat

class UiUtil {
    companion object {
        @Composable
        fun getSystemRoundedCorners(context: Context = LocalContext.current): Dp =
            try {
                val resource =
                    context.resources.getIdentifier("rounded_corner_radius", "dimen", "android")

                if (resource > 0) {
                    ResourcesCompat.getFloat(context.resources, resource).dp
                } else {
                    32.dp
                }
            } catch (_: Exception) {
                32.dp
            }

    }
}

