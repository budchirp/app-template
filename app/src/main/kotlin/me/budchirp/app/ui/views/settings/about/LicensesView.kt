@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.ui.views.settings.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.util.withContext
import me.budchirp.app.R
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.navigation.Route

@Composable
fun LicensesView(navController: NavHostController) {
    val context = LocalContext.current

    val libraries = Libs.Builder().withContext(ctx = context).build().libraries

    val selectedLibrary = rememberSaveable { mutableStateOf<Library?>(null) }

    if (selectedLibrary.value != null) {
        AlertDialog(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            title = { Text(text = selectedLibrary.value?.name ?: "") },
            onDismissRequest = {
                selectedLibrary.value = null
            },
            confirmButton = {},
            dismissButton = {
                FilledTonalButton(
                    onClick = {
                        selectedLibrary.value = null
                    }
                ) { Text(text = stringResource(R.string.close)) }
            },
            text = {
                Text(
                    text =
                    selectedLibrary.value?.licenses?.firstOrNull()?.licenseContent
                        ?: ""
                )
            }
        )
    }

    Layout(
        navController = navController,
        title = stringResource(id = Route.Licenses.title)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(libraries) { library ->
                ListItem(
                    title = library.name,
                    description =
                    library.developers.joinToString(separator = ", ") {
                        it.name ?: ""
                    },
                    lastItem = { Text(text = "v${library.artifactVersion ?: "1.0.0"}") }
                ) {
                    selectedLibrary.value = library
                }
            }
        }
    }
}