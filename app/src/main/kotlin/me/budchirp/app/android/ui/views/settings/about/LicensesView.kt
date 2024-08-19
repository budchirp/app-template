@file:OptIn(ExperimentalMaterial3Api::class)

package me.budchirp.app.android.ui.views.settings.about

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Developer
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.util.withContext
import me.budchirp.app.android.R
import me.budchirp.app.android.ui.composables.ListItem

@Composable
fun LicensesView() {
    val context: Context = LocalContext.current

    val libraries: List<Library> =
        Libs
            .Builder()
            .withContext(ctx = context)
            .build()
            .libraries

    var selectedLibrary: Library? by
        remember { mutableStateOf<Library?>(null) }

    if (selectedLibrary != null) {
        AlertDialog(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
            title = { Text(text = selectedLibrary?.name ?: "") },
            onDismissRequest = {
                selectedLibrary = null
            },
            confirmButton = {},
            dismissButton = {
                FilledTonalButton(
                    onClick = {
                        selectedLibrary = null
                    },
                ) { Text(text = stringResource(R.string.close)) }
            },
            text = {
                Text(
                    text =
                        selectedLibrary
                            ?.licenses
                            ?.firstOrNull()
                            ?.licenseContent
                            ?: "",
                )
            },
        )
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(libraries) { library: Library ->
            ListItem(
                title = library.name,
                description =
                    library.developers.joinToString(separator = ", ") { developer: Developer ->
                        developer.name ?: ""
                    },
                onClick = {
                    selectedLibrary = library
                },
                lastItem = { Text(text = "v${library.artifactVersion ?: "1.0.0"}") },
            )
        }
    }
}