package dev.cankolay.app.android.presentation.view.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import dev.cankolay.app.android.presentation.composable.CardStackList
import dev.cankolay.app.android.presentation.composable.CardStackListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn
import dev.cankolay.app.android.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagesView() {
    val context = LocalContext.current

    AppLayout(route = Route.Languages) {
        val languages = arrayOf("en", "tr")
        val currentLanguage =
            AppCompatDelegate.getApplicationLocales().toLanguageTags().ifEmpty { "en" }

        AppLazyColumn {
            item {
                CardStackList(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp),
                    items = languages.map { language ->
                        val onClick = {
                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(language),
                            )
                        }

                        CardStackListItem(
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
                            leadingContent = {
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
                    })
            }
        }
    }
}