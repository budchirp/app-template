package dev.cankolay.app.android.presentation.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import dev.cankolay.app.android.domain.model.application.MaterialYou
import dev.cankolay.app.android.domain.model.application.SettingsState
import dev.cankolay.app.android.domain.model.application.Theme

val APP_COLOR = Color(
    color = 0xFF2563EB
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(
    settingsState: SettingsState,
    content: @Composable () -> Unit,
) {
    val isDark = isDark(state = settingsState)

    val context = LocalContext.current
    val view = LocalView.current

    val window = context.findWindow()
    window?.let {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
    }

    val wallpaperColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) colorResource(
        id = android.R.color.system_accent1_500
    ) else APP_COLOR

    MaterialExpressiveTheme(
        colorScheme = rememberDynamicColorScheme(
            isAmoled = settingsState.isAmoled,
            isDark = isDark,
            seedColor = when (settingsState.materialYou) {
                is MaterialYou.WALLPAPER -> wallpaperColor
                is MaterialYou.SEED -> settingsState.materialYou.color

                is MaterialYou.OFF -> APP_COLOR
            },
            style = PaletteStyle.Expressive,
            specVersion = ColorSpec.SpecVersion.SPEC_2025
        ),
        shapes = shapes,
        motionScheme = MotionScheme.expressive(),
        content = content
    )
}

@Composable
fun isDark(state: SettingsState) = when (state.theme) {
    Theme.SYSTEM -> isSystemInDarkTheme()
    Theme.DARK -> true
    Theme.LIGHT -> false
}

fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }