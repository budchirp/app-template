package dev.cankolay.app.android.presentation.view.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import dev.cankolay.app.android.presentation.R
import dev.cankolay.app.android.presentation.composable.CardStackList
import dev.cankolay.app.android.presentation.composable.CardStackListItem
import dev.cankolay.app.android.presentation.composable.layout.AppLayout
import dev.cankolay.app.android.presentation.composable.layout.AppLazyColumn
import dev.cankolay.app.android.presentation.navigation.route.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguagesView() {
    AppLayout(route = Route.Languages) {
        val languages = mapOf("en" to R.string.en, "tr" to R.string.tr)
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
                                LocaleListCompat.forLanguageTags(language.key),
                            )
                        }

                        CardStackListItem(
                            title =
                                stringResource(
                                    id = language.value,
                                ),
                            onClick = onClick,
                            leadingContent = {
                                RadioButton(
                                    selected =
                                        currentLanguage.contains(
                                            other = language.key,
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