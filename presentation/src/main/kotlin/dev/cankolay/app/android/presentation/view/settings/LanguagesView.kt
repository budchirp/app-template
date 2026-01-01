package dev.cankolay.app.android.presentation.view.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import dev.cankolay.app.android.presentation.composable.ListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagesView() {
    val context = LocalContext.current

    AppLayout(route = Route.Languages) {
        val languages = arrayOf("en", "tr")
        val currentLanguage =
            AppCompatDelegate.getApplicationLocales().toLanguageTags().ifEmpty { "en" }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = languages) { language ->
                val onClick = {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(language),
                    )
                }

                ListItem(
                    title =
                        stringResource(
                            id =
                                context.resources.getIdentifier(
                                    language,
                                    "string",
                                    context.packageName,
                                ),
                        ),
                    onClick = onClick,
                    contentPadding = PaddingValues(all = 16.dp),
                    leadingIcon = {
                        RadioButton(
                            selected =
                                currentLanguage.contains(
                                    other = language,
                                    ignoreCase = true,
                                ),
                            onClick = onClick,
                        )
                    },
                )
            }
        }
    }
}