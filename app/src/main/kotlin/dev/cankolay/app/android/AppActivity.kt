package dev.cankolay.app.android

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.cankolay.app.android.presentation.AppUI
import dev.cankolay.app.android.presentation.viewmodel.SettingsViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            settingsViewModel.state.value == null
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView,
        ) { view: View, insets: WindowInsetsCompat ->
            view.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {
            AppUI()
        }
    }
}