package me.budchirp.app.android.ui.views.settings.appearance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.android.ui.navigation.Route
import me.budchirp.app.android.ui.viewmodels.SettingsViewModel

@Composable
fun MaterialYouView(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val settingsData = settingsViewModel.getSettingsData()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    .toggleable(value = settingsData.value.materialYou) { materialYou ->
                        settingsViewModel.updateMaterialYou(
                            materialYou = materialYou,
                            settingsData = settingsData.value
                        )
                    }
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = Route.MaterialYou.title), modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f),
                    style = MaterialTheme.typography.titleMedium
                )

                Switch(
                    modifier = Modifier
                        .padding(start = 16.dp),
                    checked = settingsData.value.materialYou,
                    onCheckedChange = { materialYou ->
                        settingsViewModel.updateMaterialYou(
                            materialYou = materialYou,
                            settingsData = settingsData.value
                        )
                    },
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(id = Route.MaterialYou.description))
            }
        }
    }
}