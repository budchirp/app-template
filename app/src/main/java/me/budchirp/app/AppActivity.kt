@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.budchirp.app.repositories.SettingsData
import me.budchirp.app.ui.navigation.AppNavGraph
import me.budchirp.app.ui.theme.AppTheme
import me.budchirp.app.ui.viewmodels.MainViewModel

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    installSplashScreen()

    setContent {
      var navController = rememberNavController()

      val viewModel: MainViewModel = viewModel()
      val settingsData: SettingsData =
          viewModel.settingsData.collectAsStateWithLifecycle(
                  initialValue = remember { runBlocking { viewModel.settingsData.first() } }
              )
              .value

      AppTheme(settingsData = settingsData) {
        AppNavGraph(
            navController = navController,
            viewModel = viewModel,
            settingsData = settingsData
        )
      }
    }
  }
}
