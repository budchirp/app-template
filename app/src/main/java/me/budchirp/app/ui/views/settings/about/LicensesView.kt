@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.views.settings.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.util.withContext
import me.budchirp.app.R
import me.budchirp.app.ui.components.Layout
import me.budchirp.app.ui.navigation.Route

@Composable
fun LicensesView(navController: NavHostController) {
  val context = LocalContext.current

  val libs = Libs.Builder().withContext(ctx = context).build()
  val libraries = libs.libraries

  val openDialog = rememberSaveable { mutableStateOf(false) }
  val selectedlibrary = rememberSaveable { mutableStateOf<Library?>(null) }

  if (openDialog.value) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp),
        title = { Text(text = selectedlibrary.value?.name ?: "") },
        onDismissRequest = {
          selectedlibrary.value = null
          openDialog.value = false
        },
        confirmButton = {},
        dismissButton = {
          FilledTonalButton(
              onClick = {
                selectedlibrary.value = null
                openDialog.value = false
              },
          ) { Text(text = stringResource(R.string.close)) }
        },
        text = {
          LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
              Text(text = selectedlibrary.value?.licenses?.firstOrNull()?.licenseContent ?: "")
            }
          }
        }
    )
  }

  Layout(
      navController = navController,
      title = stringResource(id = Route.Licenses.label),
      showBack = true
  ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(libraries) { library ->
        Row(
            modifier =
                Modifier.fillMaxWidth().clickable {
                  selectedlibrary.value = library
                  openDialog.value = true
                }
        ) {
          ListItem(
              headlineContent = { Text(text = library.name) },
              supportingContent = {
                Row {
                  library.developers.forEach { developer -> Text(text = developer.name ?: "") }
                }
              },
              trailingContent = { Text(text = "v${library.artifactVersion}") },
          )
        }
      }
    }
  }
}
