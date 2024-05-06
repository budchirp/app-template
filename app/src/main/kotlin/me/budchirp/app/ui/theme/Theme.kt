package me.budchirp.app.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.ui.viewmodels.AppViewModel

enum class Theme(val type: String) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light")
}

val DarkColorScheme = darkColorScheme()
val LightColorScheme = lightColorScheme()

private tailrec fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }

@Composable
fun AppTheme(appViewModel: AppViewModel = hiltViewModel(), content: @Composable () -> Unit) {
    val settingsData = appViewModel.getSettingsData()

    val context = LocalContext.current

    val darkTheme =
        when (settingsData.value.theme.type) {
            Theme.SYSTEM.type -> isSystemInDarkTheme()
            Theme.DARK.type -> true
            Theme.LIGHT.type -> false
            else -> isSystemInDarkTheme()
        }

    val isS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme =
        when {
            isS && settingsData.value.materialYou && darkTheme -> dynamicDarkColorScheme(
                context
            )

            isS && settingsData.value.materialYou && !darkTheme -> dynamicLightColorScheme(
                context
            )

            darkTheme -> DarkColorScheme
            !darkTheme -> LightColorScheme

            else -> LightColorScheme
        }

    val window = LocalView.current.context.findWindow()
    val view = LocalView.current

    window?.let {
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
    }

    MaterialTheme(colorScheme = colorScheme, content = content)
}