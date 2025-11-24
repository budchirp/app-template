package dev.cankolay.app.android.presentation.view.settings.appearance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.viewmodel.SettingsEvent
import dev.cankolay.app.android.presentation.viewmodel.SettingsViewModel

@Composable
fun MaterialYouView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val state by settingsViewModel.state.collectAsState()

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        item {
            state?.let { state ->
                val onClick: (Boolean) -> Unit = { materialYou ->
                    settingsViewModel.onEvent(
                        event = SettingsEvent.UpdateSettings(
                            settingsState = state.copy(
                                materialYou = materialYou
                            )
                        )
                    )
                }

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(shape = MaterialTheme.shapes.extraLarge)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                            )
                            .toggleable(
                                value = state.materialYou,
                                onValueChange = onClick
                            )
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.appearance_material_you),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(weight = 1f),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    Switch(
                        modifier =
                            Modifier
                                .padding(start = 16.dp),
                        checked = state.materialYou,
                        onCheckedChange = onClick,
                    )
                }
            }
        }

        item {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
            ) {
                Text(text = stringResource(id = R.string.appearance_material_you_desc))
            }
        }
    }
}