package dev.cankolay.app.android.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.cankolay.app.android.presentation.composable.layout.AppMainLayout
import dev.cankolay.app.android.presentation.composition.ProvideNavBackStack
import dev.cankolay.app.android.presentation.navigation.AppNavigation
import dev.cankolay.app.android.presentation.theme.AppTheme
import dev.cankolay.app.android.presentation.viewmodel.SettingsViewModel

@Composable
fun AppUI(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val state by settingsViewModel.state.collectAsState()
    state?.let { state ->
        AppTheme(settingsState = state) {
            ProvideNavBackStack {
                AppMainLayout {
                    AppNavigation()
                }
            }
        }
    }
}