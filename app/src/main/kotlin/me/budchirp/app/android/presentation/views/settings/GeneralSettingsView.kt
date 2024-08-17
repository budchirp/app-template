package me.budchirp.app.android.presentation.views.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.android.R
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import me.budchirp.app.android.presentation.composables.ListItem
import me.budchirp.app.android.viewmodel.settings.SettingsViewModel

@Composable
fun GeneralSettingsView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val settings: Settings by settingsViewModel.getSettings()

    val isOpen: MutableState<Boolean> =
        remember {
            mutableStateOf<Boolean>(false)
        }

    val inputValue: MutableState<String> =
        remember {
            mutableStateOf<String>(settings.exampleField)
        }

    if (isOpen.value) {
        AlertDialog(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            title = {
                Text(text = settings.exampleField)
            },
            onDismissRequest = { isOpen.value = false },
            confirmButton = {
                Button(onClick = {
                    settingsViewModel.updateSettings(
                        settings = NullableSettings(exampleField = inputValue.value),
                    )

                    isOpen.value = false
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                FilledTonalButton(onClick = { isOpen.value = false }) {
                    Text(text = stringResource(id = R.string.close))
                }
            },
            text = {
                OutlinedTextField(
                    value = inputValue.value,
                    onValueChange = { value: String ->
                        inputValue.value = value
                    },
                )
            },
        )
    }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        item {
            ListItem(
                title = "Example field",
                description = "Example field that opens a dialog",
                onClick = {
                    isOpen.value = true
                },
            )
        }
    }
}