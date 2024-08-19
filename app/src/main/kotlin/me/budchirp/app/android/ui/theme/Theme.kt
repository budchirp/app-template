package me.budchirp.app.android.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.android.data.datastore.model.Settings
import me.budchirp.app.android.viewmodel.SettingsViewModel

enum class Theme(
    val type: String,
) {
    SYSTEM("system"),
    DARK("dark"),
    LIGHT("light"),
}

val DarkColorScheme: ColorScheme = darkColorScheme()
val LightColorScheme: ColorScheme = lightColorScheme()

private tailrec fun Context.findWindow(): Window? =
    when (this) {
        is Activity -> window
        is ContextWrapper -> baseContext.findWindow()
        else -> null
    }

@Composable
fun AppTheme(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit,
) {
    val context: Context = LocalContext.current

    val settingsData: Settings by settingsViewModel.getSettings()

    val darkTheme: Boolean =
        when (settingsData.theme.type) {
            Theme.SYSTEM.type -> isSystemInDarkTheme()
            Theme.DARK.type -> true
            Theme.LIGHT.type -> false
            else -> isSystemInDarkTheme()
        }

    val isAndroid12Available: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colorScheme: ColorScheme =
        when {
            isAndroid12Available && settingsData.materialYou && darkTheme ->
                dynamicDarkColorScheme(
                    context,
                )

            isAndroid12Available && settingsData.materialYou && !darkTheme ->
                dynamicLightColorScheme(
                    context,
                )

            darkTheme -> DarkColorScheme
            !darkTheme -> LightColorScheme

            else -> LightColorScheme
        }

    val window: Window? = LocalView.current.context.findWindow()
    val view: View = LocalView.current

    window?.let {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
    }

    MaterialTheme(colorScheme = colorScheme, content = content)
}