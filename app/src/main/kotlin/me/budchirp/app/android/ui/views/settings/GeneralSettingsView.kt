package me.budchirp.app.android.ui.views.settings

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.budchirp.app.android.R
import me.budchirp.app.android.data.datastore.model.NullableSettings
import me.budchirp.app.android.data.datastore.model.Settings
import me.budchirp.app.android.ui.composables.ListItem
import me.budchirp.app.android.viewmodel.SettingsViewModel

@Composable
fun GeneralSettingsView(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val settings: Settings by settingsViewModel.getSettings()

    var isOpen: Boolean by
    remember {
        mutableStateOf<Boolean>(value = false)
    }

    var inputValue: String by
    remember {
        mutableStateOf<String>(value = settings.exampleField)
    }

    if (isOpen) {
        AlertDialog(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            title = {
                Text(text = settings.exampleField)
            },
            onDismissRequest = { isOpen = false },
            confirmButton = {
                Button(onClick = {
                    settingsViewModel.updateSettings(
                        settings = NullableSettings(exampleField = inputValue),
                    )

                    isOpen = false
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                FilledTonalButton(onClick = { isOpen = false }) {
                    Text(text = stringResource(id = R.string.close))
                }
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inputValue,
                    onValueChange = { value: String ->
                        inputValue = value
                    },
                )
            },
        )
    }

    LazyColumn(
        modifier =
        Modifier
            .fillMaxSize(),
    ) {
        item {
            ListItem(
                title = "Example field",
                description = "Example field that opens a dialog",
                onClick = {
                    isOpen = true
                },
            )
        }
    }
}