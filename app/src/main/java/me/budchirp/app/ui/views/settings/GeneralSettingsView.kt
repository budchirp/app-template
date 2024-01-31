@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.views.settings

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import me.budchirp.app.R
import me.budchirp.app.repositories.SettingsData
import me.budchirp.app.ui.components.Layout
import me.budchirp.app.ui.navigation.Route
import me.budchirp.app.ui.theme.Theme
import me.budchirp.app.ui.viewmodels.MainViewModel

@Composable
fun GeneralSettingsView(
    navController: NavHostController,
    viewModel: MainViewModel,
    settingsData: SettingsData
) {
  val context = LocalContext.current

  val openDialog = rememberSaveable { mutableStateOf(false) }
  val selectedTheme = rememberSaveable { mutableStateOf(settingsData.theme) }

  if (openDialog.value) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp),
        title = { Text(text = stringResource(id = R.string.general_settings_theme)) },
        onDismissRequest = { openDialog.value = false },
        confirmButton = {
          Button(
              onClick = {
                viewModel.updateTheme(theme = selectedTheme.value, settingsData = settingsData)
                openDialog.value = false
              }
          ) { Text(text = stringResource(id = R.string.apply)) }
        },
        dismissButton = {
          FilledTonalButton(onClick = { openDialog.value = false }) {
            Text(text = stringResource(R.string.close))
          }
        },
        text = {
          Column {
            Theme.values().forEach { value ->
              Row(
                  modifier = Modifier.fillMaxWidth().clickable { selectedTheme.value = value },
                  verticalAlignment = Alignment.CenterVertically
              ) {
                RadioButton(
                    selected = value == selectedTheme.value,
                    onClick = { selectedTheme.value = value }
                )
                Text(
                    text =
                        stringResource(
                            id =
                                context.resources.getIdentifier(
                                    value.type,
                                    "string",
                                    context.packageName
                                )
                        )
                )
              }
            }
          }
        }
    )
  }

  Layout(
      navController = navController,
      title = stringResource(id = Route.GeneralSettings.label),
      showBack = true
  ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      item {
        Row(modifier = Modifier.fillMaxWidth().clickable { openDialog.value = true }) {
          ListItem(
              headlineContent = {
                Text(
                    text = stringResource(id = R.string.general_settings_theme),
                    style = MaterialTheme.typography.titleMedium
                )
              },
              supportingContent = {
                Text(text = stringResource(id = R.string.general_settings_theme_desc))
              },
              trailingContent = {
                FilledTonalButton(onClick = { openDialog.value = true }) {
                  Text(
                      text =
                          stringResource(
                              id =
                                  context.resources.getIdentifier(
                                      settingsData.theme.type,
                                      "string",
                                      context.packageName
                                  )
                          )
                  )
                }
              }
          )
        }
      }

      item {
        Row(
            modifier =
                Modifier.fillMaxWidth().clickable {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    viewModel.updateMaterialYou(
                        materialYou = !settingsData.materialYou,
                        settingsData = settingsData
                    )
                  }
                }
        ) {
          ListItem(
              headlineContent = {
                Text(
                    text = stringResource(id = R.string.general_settings_material_you),
                    style = MaterialTheme.typography.titleMedium
                )
              },
              supportingContent = {
                Text(
                    text =
                        stringResource(
                            id =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                                    R.string.general_settings_material_you_desc
                                else R.string.general_settings_material_you_desc_unsupported
                        )
                )
              },
              trailingContent = {
                Switch(
                    enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
                    checked = settingsData.materialYou,
                    onCheckedChange = {
                      viewModel.updateMaterialYou(
                          materialYou = !settingsData.materialYou,
                          settingsData = settingsData
                      )
                    }
                )
              }
          )
        }
      }
    }
  }
}
