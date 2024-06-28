package me.budchirp.app.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.budchirp.app.android.ui.composables.AppLayout
import me.budchirp.app.android.ui.navigation.AppNavGraph
import me.budchirp.app.android.ui.theme.AppTheme
import me.budchirp.app.android.ui.viewmodels.SettingsViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !settingsViewModel.isReady.value
            }
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            view.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {
            val navController = rememberNavController()

            AppTheme {
                AppLayout(navController = navController) { drawerState, drawerScope ->
                    AppNavGraph(
                        navController = navController,
                        drawerState = drawerState, drawerScope = drawerScope
                    )
                }
            }
        }
    }
}