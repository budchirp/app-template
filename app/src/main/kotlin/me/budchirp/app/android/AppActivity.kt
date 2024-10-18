package me.budchirp.app.android

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
import me.budchirp.app.android.ui.composables.AppLayout
import me.budchirp.app.android.ui.composition.ProvideDrawerState
import me.budchirp.app.android.ui.composition.ProvideNavController
import me.budchirp.app.android.ui.navigation.AppNavGraph
import me.budchirp.app.android.ui.theme.AppTheme
import me.budchirp.app.android.viewmodel.AppViewModel
import me.budchirp.app.android.viewmodel.SettingsViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
    private val appViewModel: AppViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                appViewModel.isLoading
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(
            window.decorView,
        ) { view: View, insets: WindowInsetsCompat ->
            view.setPadding(0, 0, 0, 0)
            insets
        }

        enableEdgeToEdge()

        setContent {
            AppTheme {
                ProvideNavController {
                    ProvideDrawerState {
                        AppLayout {
                            AppNavGraph()
                        }
                    }
                }
            }
        }
    }
}