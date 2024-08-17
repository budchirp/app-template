package me.budchirp.app.android.presentation.views.settings.appearance

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import me.budchirp.app.android.presentation.composables.ListItem
import me.budchirp.app.android.presentation.theme.Theme
import me.budchirp.app.android.viewmodel.settings.SettingsViewModel

@Composable
fun ThemeView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val context: Context = LocalContext.current

    val settings: Settings by settingsViewModel.getSettings()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(Theme.entries) { theme: Theme ->
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
                onClick = {
                    settingsViewModel.updateSettings(
                        settings = NullableSettings(theme = theme),
                    )
                },
                firstItem = {
                    RadioButton(
                        selected = theme == settings.theme,
                        onClick = {
                            settingsViewModel.updateSettings(
                                settings = NullableSettings(theme = theme),
                            )
                        },
                    )
                },
            )
        }
    }
}