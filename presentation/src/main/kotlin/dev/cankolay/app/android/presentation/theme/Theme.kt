package dev.cankolay.app.android.presentation.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import dev.cankolay.app.android.domain.model.application.SettingsState
import dev.cankolay.app.android.domain.model.application.Theme

@Composable
fun AppTheme(
    settingsState: SettingsState,
    context: Context = LocalContext.current,
    view: View = LocalView.current,
    content: @Composable () -> Unit,
) {
    val isDark =
        when (settingsState.theme.type) {
            Theme.SYSTEM.type -> isSystemInDarkTheme()
            Theme.DARK.type -> true
            Theme.LIGHT.type -> false
            else -> isSystemInDarkTheme()
        }

    val isMonet = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme =
        when {
            isMonet && settingsState.materialYou && isDark ->
                dynamicDarkColorScheme(
                    context,
                )

            isMonet && settingsState.materialYou && !isDark ->
                dynamicLightColorScheme(
                    context,
                )

            isDark -> DarkColorScheme
            !isDark -> LightColorScheme

            else -> LightColorScheme
        }

    val window = view.context.findWindow()
    window?.let {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
    }

    MaterialTheme(colorScheme = colorScheme, shapes = shapes, content = content)
}

fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }