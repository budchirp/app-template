package me.budchirp.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.budchirp.app.ui.composables.AppLayout
import me.budchirp.app.ui.navigation.AppNavGraph
import me.budchirp.app.ui.theme.AppTheme
import me.budchirp.app.ui.viewmodels.AppViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !appViewModel.isReady.value
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
                AppLayout(navController = navController) { innerPadding ->
                    AppNavGraph(navController = navController, innerPadding = innerPadding)
                }
            }
        }
    }
}