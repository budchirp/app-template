package dev.cankolay.app.android.presentation.view.settings.appearance

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.cankolay.app.android.domain.model.application.Theme
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.CardStack
import dev.cankolay.app.android.presentation.composable.CardStackList
import dev.cankolay.app.android.presentation.composable.CardStackListItem
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn
import dev.cankolay.app.android.presentation.navigation.Route
import dev.cankolay.app.android.presentation.viewmodel.SettingsEvent
import dev.cankolay.app.android.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val context = LocalContext.current

    AppLayout(route = Route.Theme) {
        val state by settingsViewModel.state.collectAsState()

        state?.let { state ->
            AppLazyColumn {
                item {
                    CardStackList(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp),
                        items = Theme.entries.map { theme ->
                            val onClick = {
                                settingsViewModel.onEvent(
                                    event = SettingsEvent.UpdateSettings(
                                        settingsState = state.copy(
                                            theme = theme
                                        )
                                    )
                                )
                            }

                            CardStackListItem(
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
                                leadingContent = {
                                    RadioButton(
                                        selected = theme == state.theme,
                                        onClick = onClick,
                                    )
                                },
                            )
                        }
                    )
                }

                item {
                    CardStack(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp),
                        items = listOf({
                            val onClick = { isAmoled: Boolean ->
                                settingsViewModel.onEvent(
                                    event = SettingsEvent.UpdateSettings(
                                        settingsState = state.copy(
                                            isAmoled = isAmoled
                                        )
                                    )
                                )
                            }

                            ListItem(
                                title = stringResource(id = R.string.amoled),
                                trailingContent = {
                                    Switch(
                                        checked = state.theme != Theme.LIGHT && state.isAmoled,
                                        enabled = state.theme != Theme.LIGHT,
                                        onCheckedChange = onClick,
                                    )
                                },
                                enabled = state.theme != Theme.LIGHT,
                                onClick = { onClick(!state.isAmoled) }
                            )
                        })
                    )
                }
            }
        }
    }
}