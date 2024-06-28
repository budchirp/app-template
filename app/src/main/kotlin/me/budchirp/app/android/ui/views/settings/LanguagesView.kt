package me.budchirp.app.android.ui.views.settings

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat
import me.budchirp.app.android.ui.composables.ListItem

@SuppressLint("DiscouragedApi")
@Composable
fun LanguagesView() {
    val context = LocalContext.current

    val languages = arrayOf("en", "tr")

    val currentLanguage =
        AppCompatDelegate.getApplicationLocales().toLanguageTags().ifEmpty { "en" }


    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(languages) { language ->
            ListItem(
                title =
                stringResource(
                    id =
                    context.resources.getIdentifier(
                        language,
                        "string",
                        context.packageName
                    )
                ),
                onClick = {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(language)
                    )
                },
                firstItem = {
                    RadioButton(
                        selected = currentLanguage.startsWith(prefix = language),
                        onClick = {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(language)
                            )
                        }
                    )
                }
            )
        }
    }
}