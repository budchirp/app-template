package me.budchirp.app.android.ui.views.settings.appearance

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.android.ui.composables.ListItem
import me.budchirp.app.android.ui.theme.Theme
import me.budchirp.app.android.ui.viewmodels.SettingsViewModel

@SuppressLint("DiscouragedApi")
@Composable
fun ThemeView(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsData = settingsViewModel.getSettingsData()

    val context = LocalContext.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(Theme.entries) { theme ->
            ListItem(
                title =
                stringResource(
                    id =
                    context.resources.getIdentifier(
                        theme.type,
                        "string",
                        context.packageName
                    )
                ),
                onClick = {
                    settingsViewModel.updateTheme(
                        theme = theme,
                        settingsData = settingsData.value
                    )
                },
                firstItem = {
                    RadioButton(
                        selected = theme == settingsData.value.theme,
                        onClick = {
                            settingsViewModel.updateTheme(
                                theme = theme,
                                settingsData = settingsData.value
                            )
                        }
                    )
                }
            )
        }
    }
}