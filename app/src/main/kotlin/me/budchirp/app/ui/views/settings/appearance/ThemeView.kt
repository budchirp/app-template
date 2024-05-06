package me.budchirp.app.ui.views.settings.appearance

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
import androidx.navigation.NavHostController
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.theme.Theme
import me.budchirp.app.ui.viewmodels.AppViewModel

@SuppressLint("DiscouragedApi")
@Composable
fun ThemeView(navController: NavHostController, appViewModel: AppViewModel = hiltViewModel()) {
    val settingsData = appViewModel.getSettingsData()

    val context = LocalContext.current

    Layout(
        navController = navController,
        title = stringResource(id = Route.Theme.title)
    ) {
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
                    firstItem = {
                        RadioButton(
                            selected = theme == settingsData.value.theme,
                            onClick = {
                                appViewModel.updateTheme(
                                    theme = theme,
                                    settingsData = settingsData.value
                                )
                            }
                        )
                    }
                ) {
                    appViewModel.updateTheme(
                        theme = theme,
                        settingsData = settingsData.value
                    )
                }
            }
        }
    }
}