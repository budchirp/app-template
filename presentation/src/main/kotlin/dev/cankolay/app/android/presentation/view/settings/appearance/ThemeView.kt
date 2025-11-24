package dev.cankolay.app.android.presentation.view.settings.appearance

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.cankolay.app.android.domain.model.application.Theme
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.viewmodel.SettingsEvent
import dev.cankolay.app.android.presentation.viewmodel.SettingsViewModel

@Composable
fun ThemeView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val state by settingsViewModel.state.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = Theme.entries) { theme ->
            state?.let { state ->
                val onClick = {
                    settingsViewModel.onEvent(
                        event = SettingsEvent.UpdateSettings(
                            settingsState = state.copy(
                                theme = theme
                            )
                        )
                    )
                }

                ListItem(
                    title =
                        stringResource(
                            id =
                                context.resources.getIdentifier(
                                    theme.type,
                                    "string",
                                    context.packageName,
                                ),
                        ),
                    onClick = onClick,
                    contentPadding = PaddingValues(all = 16.dp),
                    leadingIcon = {
                        RadioButton(
                            selected = theme == state.theme,
                            onClick = onClick,
                        )
                    },
                )
            }
        }
    }
}