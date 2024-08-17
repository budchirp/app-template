package me.budchirp.app.android

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import me.budchirp.app.android.presentation.composables.AppLayout
import me.budchirp.app.android.presentation.navigation.AppNavGraph
import me.budchirp.app.android.presentation.theme.AppTheme
import me.budchirp.app.android.viewmodel.settings.SettingsViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !settingsViewModel.isReady.value
            }
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView,
        ) { view: View, insets: WindowInsetsCompat ->
            view.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {
            val navController: NavHostController = rememberNavController()

            val isReady: Boolean by
                settingsViewModel.isReady.collectAsStateWithLifecycle()

            AppTheme {
                if (isReady) {
                    AppLayout(
                        navController = navController,
                    ) { drawerState: DrawerState, drawerScope: CoroutineScope ->
                        AppNavGraph(
                            navController = navController,
                            drawerState = drawerState,
                            drawerScope = drawerScope,
                        )
                    }
                }
            }
        }
    }
}