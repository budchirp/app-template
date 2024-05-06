package me.budchirp.app.ui.views.settings

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
import androidx.navigation.NavHostController
import me.budchirp.app.ui.composables.Layout
import me.budchirp.app.ui.composables.ListItem
import me.budchirp.app.ui.navigation.Route

@SuppressLint("DiscouragedApi")
@Composable
fun LanguagesView(navController: NavHostController) {
    val context = LocalContext.current

    val languages = arrayOf("en", "tr")

    val currentLanguage =
        AppCompatDelegate.getApplicationLocales().toLanguageTags().ifEmpty { "en" }

    Layout(
        navController = navController,
        title = stringResource(id = Route.Languages.title)
    ) {
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
                    firstItem = {
                        RadioButton(
                            selected = language == currentLanguage,
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags(language)
                                )
                            }
                        )
                    }
                ) {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(language)
                    )
                }
            }
        }
    }
}