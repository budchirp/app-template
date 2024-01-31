package me.budchirp.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import me.budchirp.app.repositories.SettingsData

enum class Theme(val type: String) {
  SYSTEM("system"),
  DARK("dark"),
  LIGHT("light")
}

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

@Composable
fun AppTheme(settingsData: SettingsData, content: @Composable () -> Unit) {
  val view = LocalView.current
  val context = LocalContext.current

  val darkTheme =
      when (settingsData.theme.type) {
        Theme.SYSTEM.type -> isSystemInDarkTheme()
        Theme.DARK.type -> true
        Theme.LIGHT.type -> false
        else -> isSystemInDarkTheme()
      }

  val colorScheme =
      when {
        settingsData.materialYou && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
      }

  SideEffect {
    val window = (view.context as Activity).window

    window.statusBarColor = Color.Transparent.toArgb()
    window.navigationBarColor = Color.Transparent.toArgb()

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    WindowCompat.setDecorFitsSystemWindows(window, false)
  }

  MaterialTheme(colorScheme = colorScheme, content = content)
}
